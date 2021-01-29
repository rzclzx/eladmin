/*
*  Copyright 2019-2020 eladmin
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.eladmin.modules.system.service.impl;

import me.eladmin.modules.system.domain.Menu;
import me.eladmin.modules.system.domain.Role;
import me.eladmin.exception.EntityExistException;
import me.eladmin.modules.system.service.dto.UserDto;
import me.eladmin.utils.*;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.RoleRepository;
import me.eladmin.modules.system.service.RoleService;
import me.eladmin.modules.system.service.dto.RoleDto;
import me.eladmin.modules.system.service.dto.RoleQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.RoleMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author el
* @date 2021-01-26
**/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Map<String,Object> queryAll(RoleQueryCriteria criteria, Pageable pageable){
        pageable.getSortOr(QueryHelp.getSort(criteria.getSort()));
        Page<Role> page = roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(roleMapper::toDto));
    }

    @Override
    public List<RoleDto> queryAll(RoleQueryCriteria criteria){
        return roleMapper.toDto(roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseGet(Role::new);
        ValidationUtil.isNull(role.getId(),"Role","id",id);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> findByUsersId(Long id) {
        return roleMapper.toDto(new ArrayList<>(roleRepository.findByUserId(id)));
    }

    @Override
    @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        Set<Role> roles = roleRepository.findByUserId(user.getId());
        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDto create(Role resources) {
        if(roleRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Role.class,"name",resources.getName());
        }
        return roleMapper.toDto(roleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {
        Role role = roleRepository.findById(resources.getId()).orElseGet(Role::new);
        ValidationUtil.isNull( role.getId(),"Role","id",resources.getId());
        Role role0 = null;
        role0 = roleRepository.findByName(resources.getName());
        if(role0 != null && !role0.getId().equals(role.getId())){
            throw new EntityExistException(Role.class,"name",resources.getName());
        }
        role.copy(resources);
        roleRepository.save(role);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            roleRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RoleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleDto role : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", role.getName());
            map.put("角色级别", role.getLevel());
            map.put("描述", role.getDescription());
            map.put("数据权限", role.getDataScope());
            map.put("创建者", role.getCreateBy());
            map.put("更新者", role.getUpdateBy());
            map.put("创建日期", role.getCreateTime());
            map.put("更新时间", role.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}