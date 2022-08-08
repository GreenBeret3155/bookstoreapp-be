package com.hust.datn.service.dto;

import com.mservice.models.PaymentResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustOrderDetailDTO {
    private CustOrderDTO order;
    private OrderInfoDTO info;
    private List<OrderItemDTO> items;
    private List<CartItemDTO> cartItems;
    private List<OrderTraceDTO> trace;
    private Long orderValue;
    private PaymentResponse paymentResponse;

    public CustOrderDetailDTO() {
    }

    public CustOrderDetailDTO(CustOrderDTO order, OrderInfoDTO info, List<OrderItemDTO> items, List<OrderTraceDTO> trace) {
        this.order = order;
        this.info = info;
        this.items = items;
        this.orderValue = order.getAmount();
        this.trace = trace;
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

    public Long getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Long orderValue) {
        this.orderValue = orderValue;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public List<OrderTraceDTO> getTrace() {
        return trace;
    }

    public void setTrace(List<OrderTraceDTO> trace) {
        this.trace = trace;
    }
}
