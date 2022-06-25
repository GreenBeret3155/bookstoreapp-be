package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.CartItem} entity.
 */
public class CartItemDTO implements Serializable {

    private Long id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private Integer isSelected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItemDTO)) {
            return false;
        }

        return id != null && id.equals(((CartItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartItemDTO{" +
            "id=" + getId() +
            "}";
    }
}
