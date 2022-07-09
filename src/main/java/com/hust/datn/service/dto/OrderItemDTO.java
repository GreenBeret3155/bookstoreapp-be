package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.OrderItem} entity.
 */
public class OrderItemDTO implements Serializable {

    private Long id;
    private Long orderId;
    private Integer quantity;
    private Long productId;
    private String name;
    private Double totalPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItemDTO orderItemDTO, Long orderId) {
        this.orderId = orderId;
        this.quantity = orderItemDTO.quantity;
        this.productId = orderItemDTO.productId;
        this.name = orderItemDTO.name;
        this.totalPrice = orderItemDTO.totalPrice;
    }

    public OrderItemDTO(CartItemDTO cartItemDTO, Long orderId) {
        this.orderId = orderId;
        this.quantity = cartItemDTO.getQuantity();
        this.productId = cartItemDTO.getProductId();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            "}";
    }
}
