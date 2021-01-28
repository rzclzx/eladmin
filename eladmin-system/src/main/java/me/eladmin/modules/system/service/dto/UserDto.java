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

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author el
* @date 2021-01-26
**/
@Data
@Getter
@Setter
public class UserDto implements Serializable {

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickName;

    /** 性别 */
    private String gender;

    /** 手机号码 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像地址 */
    private String avatarName;

    /** 头像真实路径 */
    private String avatarPath;

    /** 密码 */
    private String password;

    /** 是否为admin账号 */
    private Boolean isAdmin;

    /** 状态：1启用、0禁用 */
    private Long enabled;

    /** 创建者 */
    private String createBy;

    /** 更新着 */
    private String updateBy;

    /** 修改密码的时间 */
    private Timestamp pwdResetTime;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** ID */
    private Long id;

    // 记一个实体通过关联表查询下面的另一个实体list（需要配置entity，Dto, Mapper）
    /** roles */
    private Set<RoleDto> roles;

    /** jobs */
    private Set<JobDto> jobs;

    // 记通过本表的部门id转化为实体 （需要配置entity，Dto,去除原始dept_id字段）
    /** dept */
    private DeptDto dept;
}