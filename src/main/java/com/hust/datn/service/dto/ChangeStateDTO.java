package com.hust.datn.service.dto;

public class ChangeStateDTO {
    private Long orderId;
    private Integer newState;
    private String description;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getNewState() {
        return newState;
    }

    public void setNewState(Integer newState) {
        this.newState = newState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
