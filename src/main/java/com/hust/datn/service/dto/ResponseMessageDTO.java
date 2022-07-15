package com.hust.datn.service.dto;

public class ResponseMessageDTO {
    private Integer status;
    private String message;

    public ResponseMessageDTO() {
    }

    public ResponseMessageDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
