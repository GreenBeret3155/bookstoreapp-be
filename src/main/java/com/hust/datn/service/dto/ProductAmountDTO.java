package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.ProductAmount} entity.
 */
public class ProductAmountDTO implements Serializable {

    private Long id;
    private Integer amount;
    private Integer available;
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAmountDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductAmountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductAmountDTO{" +
            "id=" + getId() +
            "}";
    }
}
