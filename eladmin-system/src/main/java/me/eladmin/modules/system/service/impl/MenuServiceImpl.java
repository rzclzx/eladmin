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
import me.eladmin.exception.EntityExistException;
import me.eladmin.exception.EntityExistException;
import me.eladmin.utils.*;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.MenuRepository;
import me.eladmin.modules.system.service.MenuService;
import me.eladmin.modules.system.service.dto.MenuDto;
import me.eladmin.modules.system.service.dto.MenuQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author el
* @date 2021-01-26
**/
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    private List<MenuDto> getTree(Menu menu, List<Menu> allMenus) {
        Long pid = menu.getId();
        List<Menu> menus = allMenus.stream()
                .filter(item -> item.getPid() == pid)
                .collect(Collectors.toList());
        List<MenuDto> menuDtoList = new LinkedList<>();
        menus.forEach(item -> {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(item, menuDto);
            menuDto.setChildren(getTree(item, allMenus));
            menuDtoList.add(menuDto);
        });
        return menuDtoList;
    }

    @Override
    public Map<String,Object> queryAll(MenuQueryCriteria criteria, Pageable pageable){
        Page<Menu> page = menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(menuMapper::toDto));
    }

    @Override
    public List<MenuDto> queryTree(MenuQueryCriteria criteria){
        List<Menu> allMenus = menuRepository.findAll();
        List<Menu> menus = allMenus.stream()
                .filter(item -> item.getPid() == null)
                .collect(Collectors.toList());
        List<MenuDto> menuList = new LinkedList<>();
        menus.forEach(item -> {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(item, menuDto);
            menuDto.setChildren(this.getTree(item, allMenus));
            menuList.add(menuDto);
        });
        return menuList;
    }

    @Override
    public List<MenuDto> queryAll(MenuQueryCriteria criteria){
        return menuMapper.toDto(menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public MenuDto findById(Long id) {
        Menu menu = menuRepository.findById(id).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(),"Menu","id",id);
        return menuMapper.toDto(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuDto create(Menu resources) {
        if(menuRepository.findByTitle(resources.getTitle()) != null){
            throw new EntityExistException(Menu.class,"title",resources.getTitle());
        }
        if(menuRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }
        return menuMapper.toDto(menuRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu resources) {
        Menu menu = menuRepository.findById(resources.getId()).orElseGet(Menu::new);
        ValidationUtil.isNull( menu.getId(),"Menu","id",resources.getId());
        Menu menu4 = null;
        menu4 = menuRepository.findByTitle(resources.getTitle());
        if(menu4 != null && !menu4.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"title",resources.getTitle());
        }
        Menu menu5 = null;
        menu5 = menuRepository.findByName(resources.getName());
        if(menu5 != null && !menu5.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }
        menu.copy(resources);
        menuRepository.save(menu);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            menuRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MenuDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDto menu : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("上级菜单ID", menu.getPid());
            map.put("子菜单数目", menu.getSubCount());
            map.put("菜单类型", menu.getType());
            map.put("菜单标题", menu.getTitle());
            map.put("组件名称", menu.getName());
            map.put("组件", menu.getComponent());
            map.put("排序", menu.getMenuSort());
            map.put("图标", menu.getIcon());
            map.put("链接地址", menu.getPath());
            map.put("是否外链", menu.getIFrame());
            map.put("缓存", menu.getCache());
            map.put("隐藏", menu.getHidden());
            map.put("权限", menu.getPermission());
            map.put("创建者", menu.getCreateBy());
            map.put("更新者", menu.getUpdateBy());
            map.put("创建日期", menu.getCreateTime());
            map.put("更新时间", menu.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}