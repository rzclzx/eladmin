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
import me.eladmin.modules.system.domain.Job;
import me.eladmin.modules.system.service.JobService;
import me.eladmin.modules.system.service.dto.JobQueryCriteria;
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
@Api(tags = "job管理")
@RequestMapping("/api/job")
public class JobController {

    private final JobService jobService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('job:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        jobService.download(jobService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询job")
    @ApiOperation("查询job")
    @PreAuthorize("@el.check('job:list')")
    public ResponseEntity<Object> query(JobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(jobService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增job")
    @ApiOperation("新增job")
    @PreAuthorize("@el.check('job:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Job resources){
        return new ResponseEntity<>(jobService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改job")
    @ApiOperation("修改job")
    @PreAuthorize("@el.check('job:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Job resources){
        jobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除job")
    @ApiOperation("删除job")
    @PreAuthorize("@el.check('job:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        jobService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}