package com.hust.datn.service.dto;

import com.hust.datn.config.Constants;

public class NotificationDTO {
    private Integer type;
    private ChatRoomDTO room;
    private ChatMessageDTO message;

    public NotificationDTO() {
    }

    public NotificationDTO(ChatRoomDTO chatRoom) {
        this.type = Constants.NOTIFICATION_TYPE.NEW_ROOM;
        this.room = chatRoom;
    }

    public NotificationDTO(Integer type, ChatRoomDTO room, ChatMessageDTO message) {
        this.type = type;
        this.room = room;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ChatRoomDTO getRoom() {
        return room;
    }

    public void setRoom(ChatRoomDTO room) {
        this.room = room;
    }

    public ChatMessageDTO getMessage() {
        return message;
    }

    public void setMessage(ChatMessageDTO message) {
        this.message = message;
    }
}
