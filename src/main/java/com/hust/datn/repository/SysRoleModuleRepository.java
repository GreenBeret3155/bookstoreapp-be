package com.hust.datn.repository;

import com.hust.datn.domain.SysRoleModule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysRoleModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleModuleRepository extends JpaRepository<SysRoleModule, Long> {
}
