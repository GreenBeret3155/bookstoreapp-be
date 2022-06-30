package com.hust.datn.service.impl;

import com.hust.datn.service.SysRoleModuleService;
import com.hust.datn.domain.SysRoleModule;
import com.hust.datn.repository.SysRoleModuleRepository;
import com.hust.datn.service.dto.SysRoleModuleDTO;
import com.hust.datn.service.mapper.SysRoleModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysRoleModule}.
 */
@Service
@Transactional
public class SysRoleModuleServiceImpl implements SysRoleModuleService {

    private final Logger log = LoggerFactory.getLogger(SysRoleModuleServiceImpl.class);

    private final SysRoleModuleRepository sysRoleModuleRepository;

    private final SysRoleModuleMapper sysRoleModuleMapper;

    public SysRoleModuleServiceImpl(SysRoleModuleRepository sysRoleModuleRepository, SysRoleModuleMapper sysRoleModuleMapper) {
        this.sysRoleModuleRepository = sysRoleModuleRepository;
        this.sysRoleModuleMapper = sysRoleModuleMapper;
    }

    @Override
    public SysRoleModuleDTO save(SysRoleModuleDTO sysRoleModuleDTO) {
        log.debug("Request to save SysRoleModule : {}", sysRoleModuleDTO);
        SysRoleModule sysRoleModule = sysRoleModuleMapper.toEntity(sysRoleModuleDTO);
        sysRoleModule = sysRoleModuleRepository.save(sysRoleModule);
        return sysRoleModuleMapper.toDto(sysRoleModule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysRoleModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysRoleModules");
        return sysRoleModuleRepository.findAll(pageable)
            .map(sysRoleModuleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SysRoleModuleDTO> findOne(Long id) {
        log.debug("Request to get SysRoleModule : {}", id);
        return sysRoleModuleRepository.findById(id)
            .map(sysRoleModuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysRoleModule : {}", id);
        sysRoleModuleRepository.deleteById(id);
    }
}
