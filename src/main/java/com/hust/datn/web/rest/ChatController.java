package com.hust.datn.web.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.datn.config.Constants;
import com.hust.datn.config.OkHttpRequestCommon;
import com.hust.datn.domain.*;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.ChatMessageService;
import com.hust.datn.service.ChatRoomService;
import com.hust.datn.service.ChatRoomUserService;
import com.hust.datn.service.UserService;
import com.hust.datn.service.dto.*;
import com.hust.datn.service.impl.AuthorServiceImpl;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import javassist.NotFoundException;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Response;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final List<ChatBotReceiveMessage> DEFAULT_ERROR_MESSAGE = new ArrayList<>(Arrays.asList(new ChatBotReceiveMessage("-99", "Bot chưa hiểu ....")));
    public static String CB_URL;

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
    @Autowired
    private UserService userService;

    public ChatController(Environment env) {
        this.CB_URL = env.getProperty("botchat.baseurl") + env.getProperty("botchat.webhook");
    }

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
        ChatRoomDTO chatRoomDTO = getChatRoom();
        return ResponseEntity.ok().body(chatRoomDTO);
    }

    @GetMapping("/api/room/conversation")
    public ResponseEntity<List<ChatMessageDTO>> userGetConversation(Pageable pageable)  {
        ChatRoomDTO chatRoomDTO = getChatRoom();
        Page<ChatMessageDTO> chatMessageDTOPage = chatMessageService.findMessageByRoom(chatRoomDTO.getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), chatMessageDTOPage);
        return ResponseEntity.ok().body(chatMessageDTOPage.getContent());
    }

    @GetMapping("/api/room/conversation/{roomId}")
    public ResponseEntity<List<ChatMessageDTO>> userGetConversation(@PathVariable()Long roomId, Pageable pageable)  {
        Page<ChatMessageDTO> chatMessageDTOPage = chatMessageService.findMessageByRoom(roomId, pageable);
        return ResponseEntity.ok().body(chatMessageDTOPage.getContent());
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDTO chatMessageDTO) throws IOException {
        System.out.println("Room:" + chatMessageDTO.getChatRoomId());
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElse(null);
        if(chatMessageDTO.getSenderId() != userId){
            return ;
        }
        Instant now = Instant.now();
        chatMessageDTO.setCreatedAt(now);
        chatMessageDTO.setId(null);
        ChatMessageDTO saved = chatMessageService.save(chatMessageDTO);
        saved.setId(null);
        log.debug(saved.getContent());
        List<Long> listAdminId = chatRoomUserService.findAllByRoomIdNotClient(chatMessageDTO.getChatRoomId()).stream().map(ChatRoomUserDTO::getUserId).collect(Collectors.toList());
        for(Long adminId : listAdminId){
            simpMessagingTemplate.convertAndSendToUser(Long.toString(adminId),"/notification",
                new NotificationDTO(Constants.NOTIFICATION_TYPE.UPDATE_ROOM,new ChatRoomDTO(chatMessageDTO.getChatRoomId(), now, saved.getContent()), null)
            );
        }
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageDTO.getChatRoomId()),"/queue/messages",saved);
    }

    @MessageMapping("/chat-bot")
    public void processMessageBot(@Payload ChatMessageDTO chatMessageDTO) throws IOException {
        System.out.println("Room - bot:" + chatMessageDTO.getChatRoomId());

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageDTO.getChatRoomId()),"/bot/messages",chatMessageDTO);
        List<ChatBotReceiveMessage> chatBotReceiveMessage = sentChatToBot(String.valueOf(chatMessageDTO.getSenderId()), chatMessageDTO.getContent());
        List<ChatMessageDTO> results = chatBotReceiveMessage.stream().map(e -> new ChatMessageDTO(e, chatMessageDTO.getChatRoomId())).collect(Collectors.toList());
        results.forEach(e -> {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageDTO.getChatRoomId()),"/bot/messages",e);
        });
    }

    private ChatRoomDTO getChatRoom(){
        String user = SecurityUtils.getCurrentUserLogin().get();
        Long userId = SecurityUtils.getCurrentUserId(userRepository).get();
        if(user == "anonymous"){

        }

        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserService.findOneByUserIdClient(userId).orElse(null);
        ChatRoomDTO chatRoomDTO;
        if(chatRoomUserDTO == null){
            List<UserDTO> userDTOList = userService.queryUserList(new UserSearchDTO("ROLE_ADMIN"));
            List<Long> listAdminId = userDTOList.stream().map(UserDTO::getId).collect(Collectors.toList());
            chatRoomDTO = chatRoomService.createRoom(userId, listAdminId);
            for(Long adminId : listAdminId) {
                simpMessagingTemplate.convertAndSendToUser(Long.toString(adminId), "/notification", new NotificationDTO(chatRoomDTO));
            }
        } else {
            chatRoomDTO = chatRoomService.findOne(chatRoomUserDTO.getChatRoomId()).orElseThrow(() -> new BadRequestAlertException("Somethingwrong", "Somethingwrong", "Somethingwrong"));
        }
        return chatRoomDTO;
    }


    private List<ChatBotReceiveMessage> sentChatToBot(String sender, String message) throws IOException {
        Response response = OkHttpRequestCommon.post(CB_URL,
            "{ \"sender\" : \"" + sender + "\"," + "\"message\" :\"" + message + "\" }",
            Headers.of(new HashMap<String, String>() {{
                put("Accept", APPLICATION_JSON_VALUE);
                put("Content-Type", APPLICATION_JSON_VALUE);
            }}),
            MediaType.parse(APPLICATION_JSON_VALUE));
        int code = response.code();
        String resBody = response.body().string();
        log.debug("bot response" + resBody);
        if(code == 200){
            JSONArray jsonArray = new JSONArray(resBody);
            ObjectMapper mapper = new ObjectMapper();
            List<ChatBotReceiveMessage> chatBotReceiveMessage = new ArrayList<>();
            for( int i = 0 ; i < jsonArray.length(); i++){
                chatBotReceiveMessage.add(mapper.readValue(jsonArray.get(i).toString(), ChatBotReceiveMessage.class));
            }

            return chatBotReceiveMessage;
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    @PostMapping("/api/pick-mod/{roomId}")
    public ResponseEntity<?> pickMod(@PathVariable()Long roomId, @RequestBody UserDTO userDTO) throws NotFoundException {
        if(userDTO.getId() == null){
            throw new BadRequestAlertException("Id null", "Id null", "Id null");
        }
        ChatRoomDTO chatRoomDTO = chatRoomService.findOne(roomId).orElseThrow(() -> new NotFoundException("Chat Room not found: " + roomId));
        List<ChatRoomUserDTO> listAdmin = chatRoomUserService.findAllByRoomIdNotClient(roomId);
        List<Long> listId = listAdmin.stream().map(ChatRoomUserDTO::getUserId).collect(Collectors.toList());
        if(!listId.contains(userDTO.getId())){
            chatRoomUserService.save(new ChatRoomUserDTO(roomId, userDTO.getId(), 0));
            simpMessagingTemplate.convertAndSendToUser(Long.toString(userDTO.getId()), "/notification", new NotificationDTO(chatRoomDTO));
        }
        return ResponseEntity.ok().body(new ResponseMessageDTO(1, "Thành công"));
    }
}
