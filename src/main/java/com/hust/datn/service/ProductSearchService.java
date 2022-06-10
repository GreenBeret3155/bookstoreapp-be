package com.hust.datn.service;

import com.hust.datn.service.dto.ProductSearchDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
    List<ProductSearchDTO> onSearchObject(String keyword) throws Exception;
}
