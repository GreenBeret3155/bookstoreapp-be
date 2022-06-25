package com.hust.datn.repository;

import com.hust.datn.domain.CartItem;

import java.util.List;

public interface CartItemCustomRepository {
    List<CartItem> saveCartItems(List<CartItem> cartItemList);

    void deleteRemovedCartItems(List<CartItem> cartItemList);
}
