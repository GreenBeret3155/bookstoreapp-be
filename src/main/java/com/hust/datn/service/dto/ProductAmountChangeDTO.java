package com.hust.datn.service.dto;

public class ProductAmountChangeDTO {
    private Long productId;
    private Integer amount;

    public ProductAmountChangeDTO() {
    }

    public ProductAmountChangeDTO(OrderItemDTO orderItemDTO) {
        this.productId = orderItemDTO.getProductId();
        this.amount = orderItemDTO.getQuantity() * -1;
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
}
