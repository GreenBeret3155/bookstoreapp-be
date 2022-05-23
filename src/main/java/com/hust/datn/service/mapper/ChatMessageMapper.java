package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ChatMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChatMessage} and its DTO {@link ChatMessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChatMessageMapper extends EntityMapper<ChatMessageDTO, ChatMessage> {



    default ChatMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        return chatMessage;
    }
}
