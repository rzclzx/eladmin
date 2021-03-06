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

import me.eladmin.modules.system.domain.Dept;
import me.eladmin.modules.system.service.dto.DeptDto;
import me.eladmin.modules.system.service.dto.DeptQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author el
* @date 2021-01-26
**/
public interface DeptService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(DeptQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<DeptDto>
    */
    List<DeptDto> queryAll(DeptQueryCriteria criteria);

    /**
     * 查询所有数据(tree)
     * @param criteria 条件参数
     * @return List<DeptDto>
     */
    List<DeptDto> queryTree(DeptQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return DeptDto
     */
    DeptDto findById(Long id);

    /**
     * 根据角色ID查询
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 根据PID查询
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);

    /**
     * 获取
     * @param deptList
     * @return
     */
    List<Long> getDeptChildren(List<Dept> deptList);

    /**
    * 创建
    * @param resources /
    * @return DeptDto
    */
    DeptDto create(Dept resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Dept resources);

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
    void download(List<DeptDto> all, HttpServletResponse response) throws IOException;
}