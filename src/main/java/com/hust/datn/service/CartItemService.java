package com.hust.datn.service;

import com.hust.datn.service.dto.CartItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.CartItem}.
 */
public interface CartItemService {

    /**
     * Save a cartItem.
     *
     * @param cartItemDTO the entity to save.
     * @return the persisted entity.
     */
    CartItemDTO save(CartItemDTO cartItemDTO);

    /**
     * Save cartItems.
     *
     * @param cartItemDTOs the entity to save.
     * @return the persisted entity.
     */
    List<CartItemDTO> saveCartItems(List<CartItemDTO> cartItemDTOs);

    /**
     * Get all the cartItems by cartId.
     *
     * @param cartId .
     * @return the list of entities.
     */
    List<CartItemDTO> findAllByCartId(Long cartId);

    /**
     * Get all the cartItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CartItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cartItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartItemDTO> findOne(Long id);

    /**
     * Delete the "cartId" cartItem.
     *
     * @param cartId the id of the entity.
     */
    void deleteCartItemsByCartId(Long cartId);

    void deleteCartItemsByProductId(List<Long> productIdList, Long cartItemId);
}
