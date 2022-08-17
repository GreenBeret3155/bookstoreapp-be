package com.hust.datn.repository;

import com.hust.datn.domain.ProductAmountTrace;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductAmountTrace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAmountTraceRepository extends JpaRepository<ProductAmountTrace, Long> {
}
