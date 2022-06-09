package com.hust.datn.service;

import com.hust.datn.service.dto.ProductSearchDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ProductSearch}.
 */
public interface ProductSearchService {

    /**
     * Save a productSearch.
     *
     * @param productSearchDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSearchDTO save(ProductSearchDTO productSearchDTO);

    /**
     * Get all the productSearches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductSearchDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productSearch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSearchDTO> findOne(Long id);

    /**
     * Delete the "id" productSearch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
