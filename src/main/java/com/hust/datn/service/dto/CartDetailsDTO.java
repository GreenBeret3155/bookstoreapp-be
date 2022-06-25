package com.hust.datn.service.dto;

import java.util.List;

public class CartDetailsDTO {
    private Long id;
    private Integer userId;
    private List<CartItemDTO> cartItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartItemDTO> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDTO> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
