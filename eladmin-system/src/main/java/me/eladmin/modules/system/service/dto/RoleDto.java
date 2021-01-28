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
import lombok.Getter;
import lombok.Setter;
import me.eladmin.modules.system.domain.Menu;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author el
* @date 2021-01-26
**/
@Data
@Setter
@Getter
public class RoleDto implements Serializable {

    /** 名称 */
    private String name;

    /** 角色级别 */
    private Integer level;

    /** 描述 */
    private String description;

    /** 数据权限 */
    private String dataScope;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** ID */
    private Long id;

    /** menus */
    private Set<MenuDto> menus;
}