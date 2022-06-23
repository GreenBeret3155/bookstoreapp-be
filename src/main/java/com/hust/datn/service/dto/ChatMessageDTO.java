package com.hust.datn.service.dto;

import com.hust.datn.domain.ChatBotReceiveMessage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * A DTO for the {@link com.hust.datn.domain.ChatMessage} entity.
 */
public class ChatMessageDTO implements Serializable {

    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private Integer contentType;
    private String content;
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(ChatBotReceiveMessage chatBotReceiveMessage, Long chatRoomId, Instant createdAt) {
        this.chatRoomId = chatRoomId;
        this.senderId = -99L;
        this.contentType = 1;
        this.content = chatBotReceiveMessage.getText();
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatMessageDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatMessageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatMessageDTO{" +
            "id=" + getId() +
            "}";
    }
}
