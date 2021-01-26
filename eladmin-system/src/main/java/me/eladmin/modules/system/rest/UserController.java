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
import me.eladmin.modules.system.domain.User;
import me.eladmin.modules.system.service.UserService;
import me.eladmin.modules.system.service.dto.UserQueryCriteria;
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
@Api(tags = "user管理")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询user")
    @ApiOperation("查询user")
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> query(UserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增user")
    @ApiOperation("新增user")
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody User resources){
        return new ResponseEntity<>(userService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改user")
    @ApiOperation("修改user")
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody User resources){
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除user")
    @ApiOperation("删除user")
    @PreAuthorize("@el.check('user:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        userService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}