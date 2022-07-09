package com.hust.datn.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.hust.datn.domain.CustOrder} entity.
 */
public class CustOrderDTO implements Serializable {

    private Long id;
    private Long orderInfoId;
    private Long userId;
    private Integer state;
    private Instant orderTime;
    private Instant updateTime;
    private String updateUser;

    public CustOrderDTO() {
    }

    public CustOrderDTO(Long orderInfoId, Long userId) {
        this.orderInfoId = orderInfoId;
        this.userId = userId;
        this.state = 0;
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
