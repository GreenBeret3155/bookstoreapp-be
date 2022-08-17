package com.hust.datn.service.impl;

import com.hust.datn.service.ProductAmountService;
import com.hust.datn.domain.ProductAmount;
import com.hust.datn.repository.ProductAmountRepository;
import com.hust.datn.service.dto.ProductAmountChangeDTO;
import com.hust.datn.service.dto.ProductAmountDTO;
import com.hust.datn.service.mapper.ProductAmountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductAmount}.
 */
@Service
@Transactional
public class ProductAmountServiceImpl implements ProductAmountService {

    private final Logger log = LoggerFactory.getLogger(ProductAmountServiceImpl.class);

    private final ProductAmountRepository productAmountRepository;

    private final ProductAmountMapper productAmountMapper;

    public ProductAmountServiceImpl(ProductAmountRepository productAmountRepository, ProductAmountMapper productAmountMapper) {
        this.productAmountRepository = productAmountRepository;
        this.productAmountMapper = productAmountMapper;
    }

    @Override
    public ProductAmountDTO save(ProductAmountDTO productAmountDTO) {
        log.debug("Request to save ProductAmount : {}", productAmountDTO);
        ProductAmount productAmount = productAmountMapper.toEntity(productAmountDTO);
        productAmount = productAmountRepository.save(productAmount);
        return productAmountMapper.toDto(productAmount);
    }

    @Override
    public void changeAvailable(ProductAmountDTO productAmountDTO) {
        productAmountRepository.changeAvailableProduct(productAmountDTO.getProductId(), productAmountDTO.getAvailable());
    }

    @Override
    public void changeAmount(ProductAmountDTO productAmountDTO) {
        productAmountRepository.changeAmountProduct(productAmountDTO.getProductId(), productAmountDTO.getAmount(), productAmountDTO.getAvailable());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductAmountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAmounts");
        return productAmountRepository.findAll(pageable)
            .map(productAmountMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAmountDTO> findOne(Long id) {
        log.debug("Request to get ProductAmount : {}", id);
        return productAmountRepository.findById(id)
            .map(productAmountMapper::toDto);
    }

    @Override
    public ProductAmountDTO findOneByPId(Long pId) {
        return productAmountMapper.toDto(productAmountRepository.findByProductId(pId));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAmount : {}", id);
        productAmountRepository.deleteById(id);
    }

    @Override
    public boolean checkAmount(List<ProductAmountChangeDTO> changeDTOS) {
        List<ProductAmount> productAmountList = productAmountRepository.findAllByProductIdIn(changeDTOS.stream().map(ProductAmountChangeDTO::getProductId).collect(Collectors.toList()));
        Map<Long,ProductAmountChangeDTO> map = changeDTOS.stream().collect(Collectors.toMap(e -> e.getProductId(), e -> e));
        for(ProductAmount productAmount : productAmountList){
            if(productAmount.getAvailable() == 0){
                return false;
            }
            ProductAmountChangeDTO productAmountChangeDTO = map.get(productAmount.getProductId());
            if(productAmountChangeDTO.getAmount() * -1 > productAmount.getAmount()){
                return false;
            }
        }
        return true;
    }
}
