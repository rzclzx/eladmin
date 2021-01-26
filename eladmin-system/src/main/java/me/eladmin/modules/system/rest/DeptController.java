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
package me.eladmin.modules.system.rest;

import me.eladmin.annotation.Log;
import me.eladmin.modules.system.domain.Dept;
import me.eladmin.modules.system.service.DeptService;
import me.eladmin.modules.system.service.dto.DeptQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author el
* @date 2021-01-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "dept管理")
@RequestMapping("/api/dept")
public class DeptController {

    private final DeptService deptService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dept:list')")
    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws IOException {
        deptService.download(deptService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询dept")
    @ApiOperation("查询dept")
    @PreAuthorize("@el.check('dept:list')")
    public ResponseEntity<Object> query(DeptQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(deptService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增dept")
    @ApiOperation("新增dept")
    @PreAuthorize("@el.check('dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources){
        return new ResponseEntity<>(deptService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改dept")
    @ApiOperation("修改dept")
    @PreAuthorize("@el.check('dept:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Dept resources){
        deptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除dept")
    @ApiOperation("删除dept")
    @PreAuthorize("@el.check('dept:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        deptService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}