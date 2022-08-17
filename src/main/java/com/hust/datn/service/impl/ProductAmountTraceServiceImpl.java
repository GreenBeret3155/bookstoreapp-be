package com.hust.datn.service.impl;

import com.hust.datn.service.ProductAmountTraceService;
import com.hust.datn.domain.ProductAmountTrace;
import com.hust.datn.repository.ProductAmountTraceRepository;
import com.hust.datn.service.dto.ProductAmountTraceDTO;
import com.hust.datn.service.mapper.ProductAmountMapper;
import com.hust.datn.service.mapper.ProductAmountTraceMapper;
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
 * Service Implementation for managing {@link ProductAmountTrace}.
 */
@Service
@Transactional
public class ProductAmountTraceServiceImpl implements ProductAmountTraceService {

    private final Logger log = LoggerFactory.getLogger(ProductAmountTraceServiceImpl.class);

    private final ProductAmountTraceRepository productAmountTraceRepository;

    private final ProductAmountTraceMapper productAmountTraceMapper;

    public ProductAmountTraceServiceImpl(ProductAmountTraceRepository productAmountTraceRepository, ProductAmountTraceMapper productAmountTraceMapper) {
        this.productAmountTraceRepository = productAmountTraceRepository;
        this.productAmountTraceMapper = productAmountTraceMapper;
    }

    @Override
    public ProductAmountTraceDTO save(ProductAmountTraceDTO productAmountTraceDTO) {
        log.debug("Request to save ProductAmountTrace : {}", productAmountTraceDTO);
        ProductAmountTrace productAmountTrace = productAmountTraceMapper.toEntity(productAmountTraceDTO);
        productAmountTrace = productAmountTraceRepository.save(productAmountTrace);
        return productAmountTraceMapper.toDto(productAmountTrace);
    }

    @Override
    public List<ProductAmountTraceDTO> saveAll(List<ProductAmountTraceDTO> list) {
        List<ProductAmountTrace> productAmountTraces = list.stream().map(productAmountTraceMapper::toEntity).collect(Collectors.toList());
        List<ProductAmountTrace> result = productAmountTraceRepository.saveAll(productAmountTraces);
        return result.stream().map(productAmountTraceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductAmountTraceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAmountTraces");
        return productAmountTraceRepository.findAll(pageable)
            .map(productAmountTraceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAmountTraceDTO> findOne(Long id) {
        log.debug("Request to get ProductAmountTrace : {}", id);
        return productAmountTraceRepository.findById(id)
            .map(productAmountTraceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAmountTrace : {}", id);
        productAmountTraceRepository.deleteById(id);
    }
}
