package com.hust.datn.repository;

import com.hust.datn.domain.CartItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CartItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCartId(Long cartId);

    List<CartItem> findAllByCartIdAndAndIsSelected(Long cartId, Integer isSelected);

    @Modifying
    @Query("delete from CartItem c where c.cartId = ?1")
    void deleteCartItemsByCartId(Long cartId);
}
