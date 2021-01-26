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
import me.eladmin.modules.system.domain.DictDetail;
import me.eladmin.modules.system.service.DictDetailService;
import me.eladmin.modules.system.service.dto.DictDetailQueryCriteria;
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
@Api(tags = "dictDetail管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {

    private final DictDetailService dictDetailService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dictDetail:list')")
    public void download(HttpServletResponse response, DictDetailQueryCriteria criteria) throws IOException {
        dictDetailService.download(dictDetailService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询dictDetail")
    @ApiOperation("查询dictDetail")
    @PreAuthorize("@el.check('dictDetail:list')")
    public ResponseEntity<Object> query(DictDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(dictDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增dictDetail")
    @ApiOperation("新增dictDetail")
    @PreAuthorize("@el.check('dictDetail:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources){
        return new ResponseEntity<>(dictDetailService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改dictDetail")
    @ApiOperation("修改dictDetail")
    @PreAuthorize("@el.check('dictDetail:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody DictDetail resources){
        dictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除dictDetail")
    @ApiOperation("删除dictDetail")
    @PreAuthorize("@el.check('dictDetail:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        dictDetailService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}