package com.hust.datn.service.impl;

import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.CustOrderService;
import com.hust.datn.domain.CustOrder;
import com.hust.datn.repository.CustOrderRepository;
import com.hust.datn.service.dto.CustOrderDTO;
import com.hust.datn.service.mapper.CustOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustOrder}.
 */
@Service
@Transactional
public class CustOrderServiceImpl implements CustOrderService {

    private final Logger log = LoggerFactory.getLogger(CustOrderServiceImpl.class);

    private final CustOrderRepository custOrderRepository;

    private final CustOrderMapper custOrderMapper;

    public CustOrderServiceImpl(CustOrderRepository custOrderRepository, CustOrderMapper custOrderMapper) {
        this.custOrderRepository = custOrderRepository;
        this.custOrderMapper = custOrderMapper;
    }

    @Override
    public CustOrderDTO save(CustOrderDTO custOrderDTO) {
        log.debug("Request to save CustOrder : {}", custOrderDTO);
        CustOrder custOrder = custOrderMapper.toEntity(custOrderDTO);
        if(custOrder.getId() == null){
            custOrder.setOrderTime(Instant.now());
        }
        String userLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
        custOrder.setUpdateTime(Instant.now());
        custOrder.setUpdateUser(userLogin);

        custOrder = custOrderRepository.save(custOrder);
        return custOrderMapper.toDto(custOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustOrders");
        return custOrderRepository.findAll(pageable)
            .map(custOrderMapper::toDto);
    }

    @Override
    public List<CustOrderDTO> findAllByUserId(Long userId) {
        return custOrderRepository.findAllByUserIdOrderByOrderTimeDesc(userId).stream().map(custOrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<CustOrderDTO> findAllByState(Integer state, Pageable pageable) {
        if(state == null){
            return custOrderRepository.findAllByOrderByIdDesc(pageable).map(custOrderMapper::toDto);
        }
        return custOrderRepository.findAllByStateOrderByIdDesc(state,pageable).map(custOrderMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustOrderDTO> findOne(Long id) {
        log.debug("Request to get CustOrder : {}", id);
        return custOrderRepository.findById(id)
            .map(custOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustOrder : {}", id);
        custOrderRepository.deleteById(id);
    }
}
