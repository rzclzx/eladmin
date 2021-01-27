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

import java.util.List;

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
    /**
     * 根据 pid 查询
     * @param pid /
     * @return /
     */
    List<Menu> findByPid(Long pid);
    /**
     * 查询顶级菜单
     * @return /
     */
    List<Menu> findByPidIsNull();
}