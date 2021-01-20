package me.eladmin.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import me.eladmin.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "sys_user")
public class User extends BaseEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    private String username;
}
