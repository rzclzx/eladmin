package me.eladmin.modules.system.repository;

import me.eladmin.modules.system.domain.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<SysUserEntity, Long>, JpaSpecificationExecutor<SysUserEntity> {
}
