package com.hust.datn.service.dto;

import com.hust.datn.config.Constants;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.hust.datn.domain.CustOrder} entity.
 */
public class CustOrderDTO implements Serializable {

    private Long id;
    private Long orderInfoId;
    private Long userId;
    private Integer state;
    private Instant orderTime;
    private Integer paymentType;
    private Long amount;
    private Instant updateTime;
    private String updateUser;
    private String message;

    public CustOrderDTO() {
    }

    public CustOrderDTO(Long orderInfoId, Long userId, Integer paymentType, List<OrderItemDTO> orderItemDTOS ) {
        this.orderInfoId = orderInfoId;
        this.userId = userId;
        this.paymentType = paymentType;
        this.state = Constants.ORDER_STATE.DANG_THANH_TOAN;
        if(paymentType == Constants.PAYMENT_TYPE.COD){
            this.state = Constants.ORDER_STATE.DANG_XU_LY;
        }
        Long v = 0l;
        for(int i = 0; i < orderItemDTOS.size(); i++){
            v += orderItemDTOS.get(i).getTotalPrice();
        };
        this.amount = v;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Instant getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Instant orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustOrderDTO)) {
            return false;
        }

        return id != null && id.equals(((CustOrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustOrderDTO{" +
            "id=" + getId() +
            "}";
    }
}
