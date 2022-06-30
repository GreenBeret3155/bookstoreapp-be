package com.hust.datn.repository;

import com.hust.datn.domain.SysModule;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SysModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysModuleRepository extends JpaRepository<SysModule, Long> {

    @Query(value = "select sm.* from sys_module sm \n" +
        "join sys_role_module srm on sm.code = srm.module_code \n" +
        "join jhi_authority ja on ja.name = srm.role_code \n" +
        "join jhi_user_authority jua on ja.name = jua.authority_name \n" +
        "join jhi_user ju on jua.user_id = ju.id \n" +
        "where ju.login = :login ", nativeQuery = true)
    List<SysModule> findModuleByLogin(@Param("login") String login);
}
