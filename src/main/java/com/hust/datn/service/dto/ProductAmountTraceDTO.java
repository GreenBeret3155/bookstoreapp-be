package com.hust.datn.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.hust.datn.domain.ProductAmountTrace} entity.
 */
public class ProductAmountTraceDTO implements Serializable {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer amount;
    private Instant updateTime;
    private String updateUser;

    public ProductAmountTraceDTO() {
    }

    public ProductAmountTraceDTO(Long orderId, String updateUser, OrderItemDTO orderItemDTO) {
        this.orderId = orderId;
        this.productId = orderItemDTO.getProductId();
        this.amount = orderItemDTO.getQuantity()*-1;
        this.updateUser = updateUser;
        this.updateTime = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAmountTraceDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductAmountTraceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductAmountTraceDTO{" +
            "id=" + getId() +
            "}";
    }
}
