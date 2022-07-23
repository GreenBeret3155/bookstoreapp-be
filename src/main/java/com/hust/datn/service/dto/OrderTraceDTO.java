package com.hust.datn.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.hust.datn.domain.OrderTrace} entity.
 */
public class OrderTraceDTO implements Serializable {

    private Long id;
    private Long orderId;
    private String description;
    private String content;
    private Integer state;
    private String updateUser;
    private Instant updateTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTraceDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderTraceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTraceDTO{" +
            "id=" + getId() +
            "}";
    }
}
