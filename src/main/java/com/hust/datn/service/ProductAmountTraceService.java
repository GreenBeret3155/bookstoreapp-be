package com.hust.datn.service;

import com.hust.datn.service.dto.ProductAmountTraceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ProductAmountTrace}.
 */
public interface ProductAmountTraceService {

    /**
     * Save a productAmountTrace.
     *
     * @param productAmountTraceDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAmountTraceDTO save(ProductAmountTraceDTO productAmountTraceDTO);

    List<ProductAmountTraceDTO> saveAll(List<ProductAmountTraceDTO> list);

    /**
     * Get all the productAmountTraces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductAmountTraceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productAmountTrace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAmountTraceDTO> findOne(Long id);

    /**
     * Delete the "id" productAmountTrace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
