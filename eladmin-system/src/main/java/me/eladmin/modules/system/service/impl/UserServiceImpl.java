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

import me.eladmin.modules.system.domain.User;
import me.eladmin.exception.EntityExistException;
import me.eladmin.exception.EntityExistException;
import me.eladmin.utils.ValidationUtil;
import me.eladmin.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.UserRepository;
import me.eladmin.modules.system.service.UserService;
import me.eladmin.modules.system.service.dto.UserDto;
import me.eladmin.modules.system.service.dto.UserQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.UserMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.eladmin.utils.PageUtil;
import me.eladmin.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author el
* @date 2021-01-26
**/
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Map<String,Object> queryAll(UserQueryCriteria criteria, Pageable pageable){
        pageable.getSortOr(QueryHelp.getSort(criteria.getSort()));
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public List<UserDto> queryAll(UserQueryCriteria criteria){
        return userMapper.toDto(userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(),"User","id",id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto create(User resources) {
        if(userRepository.findByUsername(resources.getUsername()) != null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }
        if(userRepository.findByEmail(resources.getEmail()) != null){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }
        return userMapper.toDto(userRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        ValidationUtil.isNull( user.getId(),"User","id",resources.getId());
        User user1 = null;
        user1 = userRepository.findByUsername(resources.getUsername());
        if(user1 != null && !user1.getId().equals(user.getId())){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }
        User user5 = null;
        user5 = userRepository.findByEmail(resources.getEmail());
        if(user5 != null && !user5.getId().equals(user.getId())){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }
        user.copy(resources);
        userRepository.save(user);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto user : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("部门名称", user.getDeptId());
            map.put("用户名", user.getUsername());
            map.put("昵称", user.getNickName());
            map.put("性别", user.getGender());
            map.put("手机号码", user.getPhone());
            map.put("邮箱", user.getEmail());
            map.put("头像地址", user.getAvatarName());
            map.put("头像真实路径", user.getAvatarPath());
            map.put("密码", user.getPassword());
            map.put("是否为admin账号", user.getIsAdmin());
            map.put("状态：1启用、0禁用", user.getEnabled());
            map.put("创建者", user.getCreateBy());
            map.put("更新着", user.getUpdateBy());
            map.put("修改密码的时间", user.getPwdResetTime());
            map.put("创建日期", user.getCreateTime());
            map.put("更新时间", user.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}