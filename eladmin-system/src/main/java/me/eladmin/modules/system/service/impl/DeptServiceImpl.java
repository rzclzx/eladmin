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

import me.eladmin.modules.system.domain.Dept;
import me.eladmin.modules.system.domain.Menu;
import me.eladmin.modules.system.service.dto.MenuDto;
import me.eladmin.utils.*;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.DeptRepository;
import me.eladmin.modules.system.service.DeptService;
import me.eladmin.modules.system.service.dto.DeptDto;
import me.eladmin.modules.system.service.dto.DeptQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;
    private final DeptMapper deptMapper;

    @Override
    public Map<String,Object> queryAll(DeptQueryCriteria criteria, Pageable pageable){
        Page<Dept> page = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(deptMapper::toDto));
    }

    @Override
    public List<DeptDto> queryAll(DeptQueryCriteria criteria){
        return deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public List<DeptDto> queryTree(DeptQueryCriteria criteria){
        List<Dept> allDepts = deptRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)
        );
        List<DeptDto> depts = Util.getTree(null, "pid", "children", deptMapper.toDto(allDepts));
        return depts;
    }

    @Override
    @Transactional
    public DeptDto findById(Long id) {
        Dept dept = deptRepository.findById(id).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getId(),"Dept","id",id);
        return deptMapper.toDto(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptDto create(Dept resources) {
        return deptMapper.toDto(deptRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        Dept dept = deptRepository.findById(resources.getId()).orElseGet(Dept::new);
        ValidationUtil.isNull( dept.getId(),"Dept","id",resources.getId());
        dept.copy(resources);
        deptRepository.save(dept);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            deptRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DeptDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDto dept : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("上级部门", dept.getPid());
            map.put("子部门数目", dept.getSubCount());
            map.put("名称", dept.getName());
            map.put("排序", dept.getDeptSort());
            map.put("状态", dept.getEnabled());
            map.put("创建者", dept.getCreateBy());
            map.put("更新者", dept.getUpdateBy());
            map.put("创建日期", dept.getCreateTime());
            map.put("更新时间", dept.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}