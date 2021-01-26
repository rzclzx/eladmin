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
import me.eladmin.modules.system.domain.Role;
import me.eladmin.modules.system.service.RoleService;
import me.eladmin.modules.system.service.dto.RoleQueryCriteria;
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
@Api(tags = "role管理")
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('role:list')")
    public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
        roleService.download(roleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询role")
    @ApiOperation("查询role")
    @PreAuthorize("@el.check('role:list')")
    public ResponseEntity<Object> query(RoleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(roleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增role")
    @ApiOperation("新增role")
    @PreAuthorize("@el.check('role:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Role resources){
        return new ResponseEntity<>(roleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改role")
    @ApiOperation("修改role")
    @PreAuthorize("@el.check('role:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Role resources){
        roleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除role")
    @ApiOperation("删除role")
    @PreAuthorize("@el.check('role:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        roleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}