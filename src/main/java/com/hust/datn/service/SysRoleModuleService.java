package com.hust.datn.service;

import com.hust.datn.service.dto.SysRoleModuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.SysRoleModule}.
 */
public interface SysRoleModuleService {

    /**
     * Save a sysRoleModule.
     *
     * @param sysRoleModuleDTO the entity to save.
     * @return the persisted entity.
     */
    SysRoleModuleDTO save(SysRoleModuleDTO sysRoleModuleDTO);

    /**
     * Get all the sysRoleModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysRoleModuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sysRoleModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysRoleModuleDTO> findOne(Long id);

    /**
     * Delete the "id" sysRoleModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
