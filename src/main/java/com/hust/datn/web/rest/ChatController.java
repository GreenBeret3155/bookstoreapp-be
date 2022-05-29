package com.hust.datn.web.rest;
import com.hust.datn.domain.ChatMessage;
import com.hust.datn.domain.ChatRoom;
import com.hust.datn.domain.ChatRoomUser;
import com.hust.datn.domain.Message;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.ChatMessageService;
import com.hust.datn.service.ChatRoomService;
import com.hust.datn.service.ChatRoomUserService;
import com.hust.datn.service.dto.CategoryDTO;
import com.hust.datn.service.dto.ChatMessageDTO;
import com.hust.datn.service.dto.ChatRoomDTO;
import com.hust.datn.service.dto.ChatRoomUserDTO;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.Instant;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatRoomUserService chatRoomUserService;
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/api/room")
    public ResponseEntity<ChatRoomDTO> userGetRoomId() throws URISyntaxException {
        String user = SecurityUtils.getCurrentUserLogin().get();
        Long userId = SecurityUtils.getCurrentUserId(userRepository).get();
        if(user == "anonymous"){

        }

        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserService.findOneByUserId(userId).orElse(null);
        ChatRoomDTO chatRoomDTO;
        if(chatRoomUserDTO == null){
            chatRoomDTO = chatRoomService.createRoom(userId);
        } else {
            chatRoomDTO = chatRoomService.findOne(chatRoomUserDTO.getChatRoomId()).orElseThrow(() -> new BadRequestAlertException("Somethingwrong", "Somethingwrong", "Somethingwrong"));
        }
        return ResponseEntity.ok().body(chatRoomDTO);
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDTO chatMessageDTO) {
        System.out.println("Room:" + chatMessageDTO.getChatRoomId());
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElse(null);
        if(chatMessageDTO.getSenderId() != userId){
            return ;
        }
        chatMessageDTO.setCreatedAt(Instant.now());
        chatMessageDTO.setId(null);
        ChatMessageDTO saved = chatMessageService.save(chatMessageDTO);
        saved.setId(null);

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageDTO.getSenderId()),"/queue/messages",saved);
    }
}
