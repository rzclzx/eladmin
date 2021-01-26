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
import me.eladmin.modules.system.domain.Dict;
import me.eladmin.modules.system.service.DictService;
import me.eladmin.modules.system.service.dto.DictQueryCriteria;
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
@Api(tags = "dict管理")
@RequestMapping("/api/dict")
public class DictController {

    private final DictService dictService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dict:list')")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        dictService.download(dictService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询dict")
    @ApiOperation("查询dict")
    @PreAuthorize("@el.check('dict:list')")
    public ResponseEntity<Object> query(DictQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(dictService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增dict")
    @ApiOperation("新增dict")
    @PreAuthorize("@el.check('dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources){
        return new ResponseEntity<>(dictService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改dict")
    @ApiOperation("修改dict")
    @PreAuthorize("@el.check('dict:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Dict resources){
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除dict")
    @ApiOperation("删除dict")
    @PreAuthorize("@el.check('dict:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        dictService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}