package com.hust.datn.service.impl;

import com.hust.datn.service.OrderTraceService;
import com.hust.datn.domain.OrderTrace;
import com.hust.datn.repository.OrderTraceRepository;
import com.hust.datn.service.dto.OrderTraceDTO;
import com.hust.datn.service.mapper.OrderTraceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderTrace}.
 */
@Service
@Transactional
public class OrderTraceServiceImpl implements OrderTraceService {

    private final Logger log = LoggerFactory.getLogger(OrderTraceServiceImpl.class);

    private final OrderTraceRepository orderTraceRepository;

    private final OrderTraceMapper orderTraceMapper;

    public OrderTraceServiceImpl(OrderTraceRepository orderTraceRepository, OrderTraceMapper orderTraceMapper) {
        this.orderTraceRepository = orderTraceRepository;
        this.orderTraceMapper = orderTraceMapper;
    }

    @Override
    public OrderTraceDTO save(OrderTraceDTO orderTraceDTO) {
        log.debug("Request to save OrderTrace : {}", orderTraceDTO);
        OrderTrace orderTrace = orderTraceMapper.toEntity(orderTraceDTO);
        orderTrace = orderTraceRepository.save(orderTrace);
        return orderTraceMapper.toDto(orderTrace);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderTraceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderTraces");
        return orderTraceRepository.findAll(pageable)
            .map(orderTraceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderTraceDTO> findOne(Long id) {
        log.debug("Request to get OrderTrace : {}", id);
        return orderTraceRepository.findById(id)
            .map(orderTraceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderTrace : {}", id);
        orderTraceRepository.deleteById(id);
    }
}
