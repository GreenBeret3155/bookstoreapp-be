package com.hust.datn.service.impl;

import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.ProductService;
import com.hust.datn.domain.Product;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.service.dto.ProductDTO;
import com.hust.datn.service.mapper.ProductMapper;
import com.hust.datn.service.mapper.custom.ProductCustomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductCustomMapper productCustomMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductCustomMapper productCustomMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productCustomMapper = productCustomMapper;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productCustomMapper.productDTOToProduct(productDTO);
        product.setUpdateUser(SecurityUtils.getCurrentUserLogin().get());
        product.setUpdateTime(Instant.now());
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productCustomMapper::productToProductDTO);
    }

    @Override
    public Page<ProductDTO> query(Long authorId, Long categoryId, String q, Integer status, Pageable pageable) {
        return productRepository.query(authorId, categoryId, q, status, pageable)
            .map(productCustomMapper::productToProductDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id)
            .map(productCustomMapper::productToProductDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
