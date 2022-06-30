package com.hust.datn.service;

import com.hust.datn.domain.SysModule;
import com.hust.datn.service.dto.SysModuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.SysModule}.
 */
public interface SysModuleService {

    /**
     * Save a sysModule.
     *
     * @param sysModuleDTO the entity to save.
     * @return the persisted entity.
     */
    SysModuleDTO save(SysModuleDTO sysModuleDTO);

    /**
     * Get all the sysModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysModuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sysModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysModuleDTO> findOne(Long id);

    /**
     * Delete the "id" sysModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<SysModuleDTO> findModuleByLogin(String login);
}
