package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ChatRoomUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChatRoomUser} and its DTO {@link ChatRoomUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChatRoomUserMapper extends EntityMapper<ChatRoomUserDTO, ChatRoomUser> {



    default ChatRoomUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setId(id);
        return chatRoomUser;
    }
}
