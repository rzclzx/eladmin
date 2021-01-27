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
import me.eladmin.modules.system.domain.Menu;
import me.eladmin.modules.system.service.MenuService;
import me.eladmin.modules.system.service.dto.MenuDto;
import me.eladmin.modules.system.service.dto.MenuQueryCriteria;
import me.eladmin.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author el
* @date 2021-01-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "menu管理")
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('menu:list')")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
        menuService.download(menuService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询menu")
    @ApiOperation("查询menu")
    @PreAuthorize("@el.check('menu:list')")
    public ResponseEntity<Object> query(MenuQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(menuService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("tree")
    @Log("查询树形menu")
    @ApiOperation("查询树形menu")
    @PreAuthorize("@el.check('menu:list')")
    public ResponseEntity<Object> queryTree(MenuQueryCriteria criteria, Pageable pageable){
        List<MenuDto> menuDtoList = menuService.queryTree(criteria);
        return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增menu")
    @ApiOperation("新增menu")
    @PreAuthorize("@el.check('menu:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Menu resources){
        return new ResponseEntity<>(menuService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改menu")
    @ApiOperation("修改menu")
    @PreAuthorize("@el.check('menu:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除menu")
    @ApiOperation("删除menu")
    @PreAuthorize("@el.check('menu:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        menuService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}