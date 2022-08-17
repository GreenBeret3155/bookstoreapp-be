package com.hust.datn.service;

import com.hust.datn.service.dto.ProductAmountChangeDTO;
import com.hust.datn.service.dto.ProductAmountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ProductAmount}.
 */
public interface ProductAmountService {

    /**
     * Save a productAmount.
     *
     * @param productAmountDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAmountDTO save(ProductAmountDTO productAmountDTO);

    void changeAvailable(ProductAmountDTO productAmountDTO);

    void changeAmount(ProductAmountDTO productAmountDTO);

    /**
     * Get all the productAmounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductAmountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productAmount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAmountDTO> findOne(Long id);

    ProductAmountDTO findOneByPId(Long pId);

    /**
     * Delete the "id" productAmount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean checkAmount(List<ProductAmountChangeDTO> changeDTOS);
}
