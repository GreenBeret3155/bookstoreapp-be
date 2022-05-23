package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ChatRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChatRoom} and its DTO {@link ChatRoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChatRoomMapper extends EntityMapper<ChatRoomDTO, ChatRoom> {



    default ChatRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(id);
        return chatRoom;
    }
}
