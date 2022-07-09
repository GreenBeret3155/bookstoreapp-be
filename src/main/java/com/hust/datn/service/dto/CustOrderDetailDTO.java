package com.hust.datn.service.dto;

import java.util.List;
import java.util.stream.Collectors;

public class CustOrderDetailDTO {
    private CustOrderDTO order;
    private OrderInfoDTO info;
    private List<OrderItemDTO> items;
    private List<CartItemDTO> cartItems;
    private Double orderValue;

    public CustOrderDetailDTO() {
    }

    public CustOrderDetailDTO(CustOrderDTO order, OrderInfoDTO info, List<OrderItemDTO> items) {
        Double v = 0d;
        this.order = order;
        this.info = info;
        this.items = items;
        for(int i = 0; i < items.size(); i++){
            v += items.get(i).getTotalPrice();
        };
        this.orderValue = v;
    }

    public CustOrderDetailDTO(OrderInfoDTO orderInfoDTO, List<CartItemDTO> cartItemsList, Long orderId) {
        this.info = orderInfoDTO;
        this.items = cartItemsList.stream().map(e -> new OrderItemDTO(e, orderId)).collect(Collectors.toList());
    }

    public CustOrderDTO getOrder() {
        return order;
    }

    public void setOrder(CustOrderDTO order) {
        this.order = order;
    }

    public OrderInfoDTO getInfo() {
        return info;
    }

    public void setInfo(OrderInfoDTO info) {
        this.info = info;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }
}
