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

import me.eladmin.modules.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @website https://el-admin.vip
* @author el
* @date 2021-01-26
**/
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    /**
    * 根据 Name 查询
    * @param name /
    * @return /
    */
    Role findByName(String name);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    @Query(value = "SELECT r.* FROM sys_role r, sys_users_roles u WHERE " +
            "r.id = u.role_id AND u.user_id = ?1",nativeQuery = true)
    Set<Role> findByUserId(Long id);
}