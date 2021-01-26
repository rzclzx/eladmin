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
package me.eladmin.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author el
* @date 2021-01-26
**/
@Data
public class MenuDto implements Serializable {

    /** ID */
    private Long id;

    /** 上级菜单ID */
    private Long pid;

    /** 子菜单数目 */
    private Integer subCount;

    /** 菜单类型 */
    private Integer type;

    /** 菜单标题 */
    private String title;

    /** 组件名称 */
    private String name;

    /** 组件 */
    private String component;

    /** 排序 */
    private Integer menuSort;

    /** 图标 */
    private String icon;

    /** 链接地址 */
    private String path;

    /** 是否外链 */
    private Boolean iFrame;

    /** 缓存 */
    private Boolean cache;

    /** 隐藏 */
    private Boolean hidden;

    /** 权限 */
    private String permission;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}