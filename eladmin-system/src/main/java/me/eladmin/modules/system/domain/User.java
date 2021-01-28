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

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author el
* @date 2021-01-26
**/
@Entity
@Data
@Getter
@Setter
@Table(name="sys_user")
public class User implements Serializable {

    @Column(name = "username",unique = true)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "nick_name")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    @Column(name = "phone")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @Column(name = "email",unique = true)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(name = "avatar_name")
    @ApiModelProperty(value = "头像地址")
    private String avatarName;

    @Column(name = "avatar_path")
    @ApiModelProperty(value = "头像真实路径")
    private String avatarPath;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "is_admin")
    @ApiModelProperty(value = "是否为admin账号")
    private Boolean isAdmin;

    @Column(name = "enabled")
    @ApiModelProperty(value = "状态：1启用、0禁用")
    private Long enabled;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新着")
    private String updateBy;

    @Column(name = "pwd_reset_time")
    @ApiModelProperty(value = "修改密码的时间")
    private Timestamp pwdResetTime;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    // 记一个实体通过关联表查询下面的另一个实体list（需要配置entity，Dto，Mapper）
    @ManyToMany
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    @ApiModelProperty(value = "角色", hidden = true)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "sys_users_jobs",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "job_id",referencedColumnName = "id")})
    @ApiModelProperty(value = "岗位", hidden = true)
    private Set<Job> jobs;

    // 记通过本表的部门id转化为实体 （需要配置entity，Dto，去除原始dept_id字段）
    @OneToOne
    @JoinColumn(name = "dept_id")
    @ApiModelProperty(value = "用户部门")
    private Dept dept;

    public void copy(User source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}