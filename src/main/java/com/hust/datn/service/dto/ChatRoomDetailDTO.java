package com.hust.datn.service.dto;

import com.hust.datn.domain.ChatRoom;
import com.hust.datn.domain.ChatRoomDetail;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A DTO for the {@link com.hust.datn.domain.ChatRoom} entity.
 */
public class ChatRoomDetailDTO implements Serializable {

    private Long id;
    private String uuid;
    private Instant lastMessageTime;
    private String lastMessageContent;
    private List<Long> listUserId;
    private UserDTO client;

    public ChatRoomDetailDTO() {
    }

    public ChatRoomDetailDTO(Long id, String uuid, Instant lastMessageTime, String lastMessageContent) {
        this.id = id;
        this.uuid = uuid;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageContent = lastMessageContent;
    }

    public ChatRoomDetailDTO(ChatRoomDetail chatRoomDetail) {
        this.id = chatRoomDetail.getId();
        this.uuid = chatRoomDetail.getUuid();
        this.lastMessageContent = chatRoomDetail.getLastMessageContent();
        this.lastMessageTime = chatRoomDetail.getLastMessageTime();
        this.listUserId = new LinkedList<>(Arrays.asList(chatRoomDetail.getUserId()));
    }

    public ChatRoomDetailDTO(ChatRoom chatRoom, List<Long> listUserId) {
        this.id = chatRoom.getId();
        this.uuid = chatRoom.getUuid();
        this.lastMessageContent = chatRoom.getLastMessageContent();
        this.lastMessageTime = chatRoom.getLastMessageTime();
        this.listUserId = listUserId;
    }

    public ChatRoomDetailDTO(ChatRoom chatRoom, List<Long> listUserId, UserDTO client) {
        this.id = chatRoom.getId();
        this.uuid = chatRoom.getUuid();
        this.lastMessageContent = chatRoom.getLastMessageContent();
        this.lastMessageTime = chatRoom.getLastMessageTime();
        this.listUserId = listUserId;
        this.client = client;
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

    public List<Long> getListUserId() {
        return listUserId;
    }

    public void setListUserId(List<Long> listUserId) {
        this.listUserId = listUserId;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }
}
