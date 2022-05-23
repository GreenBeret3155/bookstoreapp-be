package com.hust.datn.web.rest;
import com.hust.datn.domain.ChatMessage;
import com.hust.datn.domain.Message;
import com.hust.datn.service.ChatMessageService;
import com.hust.datn.service.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }

//    @MessageMapping("/chat")
//    public void processMessage(@Payload ChatMessageDTO chatMessageDTO) {
//        var chatId = chatRoomService
//            .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
//        chatMessage.setChatId(chatId.get());
//
//        ChatMessageDTO saved = chatMessageService.save(chatMessageDTO);
//
//        simpMessagingTemplate.convertAndSendToUser(chatMessageDTO.getRecipientId(),"/queue/messages");
//    }
}
