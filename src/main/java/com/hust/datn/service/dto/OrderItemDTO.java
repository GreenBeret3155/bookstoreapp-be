package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.OrderItem} entity.
 */
public class OrderItemDTO implements Serializable {

    private Long id;
    private Long orderId;
    private Integer quantity;
    private Long price;
    private Long discount;
    private String thumbnailUrl;
    private Long productId;
    private String name;
    private Long totalPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItemDTO orderItemDTO, Long orderId) {
        this.orderId = orderId;
        this.quantity = orderItemDTO.getQuantity();
        this.productId = orderItemDTO.getProductId();
        this.name = orderItemDTO.getName();
        this.totalPrice = orderItemDTO.getTotalPrice();
        this.discount = orderItemDTO.getDiscount();
        this.price = orderItemDTO.getDiscount();
        this.thumbnailUrl = orderItemDTO.getThumbnailUrl();
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

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
