package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.ChatRoomUser} entity.
 */
public class ChatRoomUserDTO implements Serializable {

    private Long id;
    private Long chatRoomId;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatRoomUserDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatRoomUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatRoomUserDTO{" +
            "id=" + getId() +
            "}";
    }
}
