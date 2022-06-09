package com.hust.datn.repository;

import com.hust.datn.domain.ProductSearch;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductSearch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSearchRepository extends JpaRepository<ProductSearch, Long> {
}
