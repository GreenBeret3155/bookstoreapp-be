package com.hust.datn.repository;

import com.hust.datn.domain.ProductAmount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProductAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAmountRepository extends JpaRepository<ProductAmount, Long> {
    @Modifying
    @Query("update ProductAmount p set p.amount = p.amount + ?2, p.available = ?3 where p.productId = ?1")
    void changeAmountProduct(Long productId, Integer change, Integer available);

    @Modifying
    @Query("update ProductAmount p set p.available = ?2 where p.productId = ?1")
    void changeAvailableProduct(Long productId, Integer available);

    List<ProductAmount> findAllByProductIdIn(List<Long> pIds);

    ProductAmount findByProductId(Long pId);
}
