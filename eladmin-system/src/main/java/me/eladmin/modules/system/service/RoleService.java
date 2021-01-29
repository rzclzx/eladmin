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
package me.eladmin.modules.system.service;

import me.eladmin.modules.system.domain.Role;
import me.eladmin.modules.system.service.dto.RoleDto;
import me.eladmin.modules.system.service.dto.RoleQueryCriteria;
import me.eladmin.modules.system.service.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author el
* @date 2021-01-26
**/
public interface RoleService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RoleDto>
    */
    List<RoleDto> queryAll(RoleQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return RoleDto
     */
    RoleDto findById(Long id);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    List<RoleDto> findByUsersId(Long id);

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

    /**
    * 创建
    * @param resources /
    * @return RoleDto
    */
    RoleDto create(Role resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Role resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<RoleDto> all, HttpServletResponse response) throws IOException;
}