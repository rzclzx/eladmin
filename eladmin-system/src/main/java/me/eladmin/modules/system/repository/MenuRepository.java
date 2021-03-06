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
package me.eladmin.modules.system.repository;

import me.eladmin.modules.system.domain.Menu;
import me.eladmin.modules.system.service.dto.MenuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
* @website https://el-admin.vip
* @author el
* @date 2021-01-26
**/
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
    /**
    * 根据 Title 查询
    * @param title /
    * @return /
    */
    Menu findByTitle(String title);
    /**
    * 根据 Name 查询
    * @param name /
    * @return /
    */
    Menu findByName(String name);

    @Query(value = "SELECT m.* FROM sys_menu m, sys_roles_menus r WHERE " +
            "m.id = r.menu_id AND r.role_id IN ?1 AND type != ?2 order by m.menu_sort asc",nativeQuery = true)
    LinkedHashSet<Menu> findByRoleIdsAndTypeNot(Set<Long> roleIds, int type);
}