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
package me.eladmin.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author el
* @date 2021-01-26
**/
@Entity
@Data
@Table(name="sys_menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "pid")
    @ApiModelProperty(value = "上级菜单ID")
    private Long pid;

    @Column(name = "sub_count")
    @ApiModelProperty(value = "子菜单数目")
    private Integer subCount;

    @Column(name = "type")
    @ApiModelProperty(value = "菜单类型")
    private Integer type;

    @Column(name = "title",unique = true)
    @ApiModelProperty(value = "菜单标题")
    private String title;

    @Column(name = "name",unique = true)
    @ApiModelProperty(value = "组件名称")
    private String name;

    @Column(name = "component")
    @ApiModelProperty(value = "组件")
    private String component;

    @Column(name = "menu_sort")
    @ApiModelProperty(value = "排序")
    private Integer menuSort;

    @Column(name = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    @Column(name = "path")
    @ApiModelProperty(value = "链接地址")
    private String path;

    @Column(name = "i_frame")
    @ApiModelProperty(value = "是否外链")
    private Boolean iFrame;

    @Column(name = "cache")
    @ApiModelProperty(value = "缓存")
    private Boolean cache;

    @Column(name = "hidden")
    @ApiModelProperty(value = "隐藏")
    private Boolean hidden;

    @Column(name = "permission")
    @ApiModelProperty(value = "权限")
    private String permission;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(Menu source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}