package com.hust.datn.service.impl;

import com.hust.datn.service.SysModuleService;
import com.hust.datn.domain.SysModule;
import com.hust.datn.repository.SysModuleRepository;
import com.hust.datn.service.dto.SysModuleDTO;
import com.hust.datn.service.mapper.SysModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysModule}.
 */
@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

    private final Logger log = LoggerFactory.getLogger(SysModuleServiceImpl.class);

    private final SysModuleRepository sysModuleRepository;

    private final SysModuleMapper sysModuleMapper;

    public SysModuleServiceImpl(SysModuleRepository sysModuleRepository, SysModuleMapper sysModuleMapper) {
        this.sysModuleRepository = sysModuleRepository;
        this.sysModuleMapper = sysModuleMapper;
    }

    @Override
    public SysModuleDTO save(SysModuleDTO sysModuleDTO) {
        log.debug("Request to save SysModule : {}", sysModuleDTO);
        SysModule sysModule = sysModuleMapper.toEntity(sysModuleDTO);
        sysModule = sysModuleRepository.save(sysModule);
        return sysModuleMapper.toDto(sysModule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysModules");
        return sysModuleRepository.findAll(pageable)
            .map(sysModuleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SysModuleDTO> findOne(Long id) {
        log.debug("Request to get SysModule : {}", id);
        return sysModuleRepository.findById(id)
            .map(sysModuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysModule : {}", id);
        sysModuleRepository.deleteById(id);
    }

    @Override
    public List<SysModuleDTO> findModuleByLogin(String login) {
        return sysModuleRepository.findModuleByLogin(login).stream().map(sysModuleMapper::toDto).collect(Collectors.toList());
    }
}
