package com.hust.datn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ChatRoom.
 */
@Entity
public class ChatRoomDetail implements Serializable {

    @Id
    private Long id;
    private String uuid;
    private Instant lastMessageTime;
    private String lastMessageContent;
    private Long userId;

    public ChatRoomDetail() {
    }

    public ChatRoomDetail(Long id, String uuid, Instant lastMessageTime, String lastMessageContent, Long userId) {
        this.id = id;
        this.uuid = uuid;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageContent = lastMessageContent;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Instant getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Instant lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
