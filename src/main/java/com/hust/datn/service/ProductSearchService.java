package com.hust.datn.service;

import com.hust.datn.service.dto.AuthorDTO;
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
     * @return the persisted entity.
     */
    List<?> onSearchObject(String keyword ,Integer type) throws Exception;

    List<?> onSearchProductByAuthorId(Integer authorId) throws Exception;

    List<?> onSearchProductByCategoryId(Integer categoryId) throws Exception;

//    List<AuthorDTO> onSearchObject(String keyword , Integer type) throws Exception;
}
