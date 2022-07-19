package com.hust.datn.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.hust.datn.domain.ChatRoom} entity.
 */
public class ChatRoomDTO implements Serializable {

    private Long id;
    private String uuid;
    private Instant lastMessageTime;
    private String lastMessageContent;

    public ChatRoomDTO() {
    }

    public ChatRoomDTO(Long id, Instant lastMessageTime, String lastMessageContent) {
        this.id = id;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageContent = lastMessageContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatRoomDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatRoomDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatRoomDTO{" +
            "id=" + getId() +
            "}";
    }
}
