package com.hust.datn.domain;

import java.time.Instant;
import java.util.Date;
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Instant status;

    public Message() {
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Instant getStatus() {
        return status;
    }

    public void setStatus(Instant status) {
        this.status = status;
    }
}
