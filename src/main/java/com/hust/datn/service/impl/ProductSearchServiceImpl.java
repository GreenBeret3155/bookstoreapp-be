package com.hust.datn.service.impl;

import com.hust.datn.service.ProductSearchService;
import com.hust.datn.domain.ProductSearch;
import com.hust.datn.repository.ProductSearchRepository;
import com.hust.datn.service.dto.ProductSearchDTO;
import com.hust.datn.service.mapper.ProductSearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductSearch}.
 */
@Service
@Transactional
public class ProductSearchServiceImpl implements ProductSearchService {

    private final Logger log = LoggerFactory.getLogger(ProductSearchServiceImpl.class);

    private final ProductSearchRepository productSearchRepository;

    private final ProductSearchMapper productSearchMapper;

    public ProductSearchServiceImpl(ProductSearchRepository productSearchRepository, ProductSearchMapper productSearchMapper) {
        this.productSearchRepository = productSearchRepository;
        this.productSearchMapper = productSearchMapper;
    }

    @Override
    public ProductSearchDTO save(ProductSearchDTO productSearchDTO) {
        log.debug("Request to save ProductSearch : {}", productSearchDTO);
        ProductSearch productSearch = productSearchMapper.toEntity(productSearchDTO);
        productSearch = productSearchRepository.save(productSearch);
        return productSearchMapper.toDto(productSearch);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSearchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSearches");
        return productSearchRepository.findAll(pageable)
            .map(productSearchMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSearchDTO> findOne(Long id) {
        log.debug("Request to get ProductSearch : {}", id);
        return productSearchRepository.findById(id)
            .map(productSearchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSearch : {}", id);
        productSearchRepository.deleteById(id);
    }
}
