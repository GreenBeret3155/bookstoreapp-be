package com.hust.datn.service.impl;

import com.hust.datn.service.OrderInfoService;
import com.hust.datn.domain.OrderInfo;
import com.hust.datn.repository.OrderInfoRepository;
import com.hust.datn.service.dto.OrderInfoDTO;
import com.hust.datn.service.mapper.OrderInfoMapper;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
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
 * Service Implementation for managing {@link OrderInfo}.
 */
@Service
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService {

    private final Logger log = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    private final OrderInfoRepository orderInfoRepository;

    private final OrderInfoMapper orderInfoMapper;

    public OrderInfoServiceImpl(OrderInfoRepository orderInfoRepository, OrderInfoMapper orderInfoMapper) {
        this.orderInfoRepository = orderInfoRepository;
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public OrderInfoDTO save(OrderInfoDTO orderInfoDTO) {
        log.debug("Request to save OrderInfo : {}", orderInfoDTO);
        OrderInfo orderInfo = orderInfoMapper.toEntity(orderInfoDTO);
        orderInfo = orderInfoRepository.save(orderInfo);
        return orderInfoMapper.toDto(orderInfo);
    }

    @Override
    public OrderInfoDTO setDefaultOrderInfo(Long defaultOrderInfoId, Long userId) {
        OrderInfo orderInfo = orderInfoRepository.findById(userId).orElseThrow(() -> new BadRequestAlertException("Not found", "Not found", "Not found"));
        orderInfoRepository.resetStateAllOrderInfos(userId);
        orderInfo.setState(1);
        return orderInfoMapper.toDto(orderInfoRepository.save(orderInfo));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderInfos");
        return orderInfoRepository.findAll(pageable)
            .map(orderInfoMapper::toDto);
    }

    @Override
    public List<OrderInfoDTO> findAllOrderInfosByUserId(Long userId) {
        return orderInfoRepository.findAllByUserId(userId).stream().map(orderInfoMapper::toDto).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderInfoDTO> findOne(Long id) {
        log.debug("Request to get OrderInfo : {}", id);
        return orderInfoRepository.findById(id)
            .map(orderInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderInfo : {}", id);
        orderInfoRepository.deleteById(id);
    }
}
