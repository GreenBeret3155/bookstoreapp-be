package com.hust.datn.service.dto;

import com.hust.datn.domain.CartItem;

import java.util.List;

public class CartDetailsDTO {
    private CartDTO cart;
    private List<CartItemDTO> cartItemList;

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public List<CartItemDTO> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDTO> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
