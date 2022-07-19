package com.hust.datn.service.impl;

import com.hust.datn.domain.ChatRoomDetail;
import com.hust.datn.domain.ChatRoomUser;
import com.hust.datn.domain.User;
import com.hust.datn.repository.ChatRoomDetailRepository;
import com.hust.datn.repository.ChatRoomUserRepository;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.service.ChatRoomService;
import com.hust.datn.domain.ChatRoom;
import com.hust.datn.repository.ChatRoomRepository;
import com.hust.datn.service.dto.ChatRoomDTO;
import com.hust.datn.service.dto.ChatRoomDetailDTO;
import com.hust.datn.service.dto.UserDTO;
import com.hust.datn.service.mapper.ChatRoomMapper;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link ChatRoom}.
 */
@Service
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

    private final Logger log = LoggerFactory.getLogger(ChatRoomServiceImpl.class);

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomDetailRepository chatRoomDetailRepository;

    @Autowired
    private UserRepository userRepository;

    private final ChatRoomMapper chatRoomMapper;

    private final ChatRoomUserRepository chatRoomUserRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper, ChatRoomUserRepository chatRoomUserRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
        this.chatRoomUserRepository = chatRoomUserRepository;
    }

    @Override
    public ChatRoomDTO save(ChatRoomDTO chatRoomDTO) {
        log.debug("Request to save ChatRoom : {}", chatRoomDTO);
        ChatRoom chatRoom = chatRoomMapper.toEntity(chatRoomDTO);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toDto(chatRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChatRooms");
        return chatRoomRepository.findAll(pageable)
            .map(chatRoomMapper::toDto);
    }

//    @Override
//    public List<ChatRoomDetailDTO> findAllByUserIdAdmin(Long userAdminId) {
//
//        List<?> chatRoomDetailList = chatRoomDetailRepository.findAllRoomsAdmin(userAdminId);
//        List<ChatRoomDetailDTO> listDetailDTO = new ArrayList<>();
//        List<ChatRoomDetail> chatRoomDetailList = chatRoomDetailRepository.findAllRoomsAdmin(userAdminId);
//        Long lastId = chatRoomDetailList.get(0).getId();
//        ChatRoomDetailDTO detailDTO = new ChatRoomDetailDTO(chatRoomDetailList.get(0));
//        for(int i = 0 ; i < chatRoomDetailList.size() ; i++){
//            if(i == chatRoomDetailList.size() - 1){
//                if(chatRoomDetailList.get(i).getId() == lastId){
//                    Set<Long> list = new HashSet<Long>(detailDTO.getListUserId());
//                    list.add(chatRoomDetailList.get(i).getUserId());
//                    detailDTO.setListUserId(list.stream().collect(Collectors.toList()));
//                } else {
//                    detailDTO = new ChatRoomDetailDTO(chatRoomDetailList.get(i));
//                }
//                listDetailDTO.add(detailDTO);
//            } else{
//                if(chatRoomDetailList.get(i).getId() == lastId){
//                    Set<Long> list = new HashSet<Long>(detailDTO.getListUserId());
//                    list.add(chatRoomDetailList.get(i).getUserId());
//                    detailDTO.setListUserId(list.stream().collect(Collectors.toList()));
//                } else {
//                    lastId = chatRoomDetailList.get(i).getId();
//                    listDetailDTO.add(detailDTO);
//                    detailDTO = new ChatRoomDetailDTO(chatRoomDetailList.get(i));
//                }
//            }
//        }
//        return listDetailDTO;
//    }

    @Override
    public Page<ChatRoomDetailDTO> findAllByUserIdAdmin(Long userAdminId, Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findAllChatRoomByAdmin(userAdminId, pageable);
        Page<ChatRoomDetailDTO> chatRoomDetailDTOS = chatRooms.map(e -> {
            List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAllByChatRoomId(e.getId());
            ChatRoomUser chatRoomUser = chatRoomUserList.stream().filter(x -> x.getIsClient() == 1).findFirst().orElseThrow(() -> new BadRequestAlertException("Something wrong!", "Something wrong!", "Something wrong!"));
            User client = userRepository.findOneById(chatRoomUser.getUserId()).orElseThrow(() -> new BadRequestAlertException("findAllByUserIdAdmin", "Something wrong!", "Something wrong!"));
            UserDTO clientDTO = new UserDTO(client);
            return new ChatRoomDetailDTO(e, chatRoomUserList.stream().map(ChatRoomUser::getUserId).collect(Collectors.toList()), clientDTO);
        });
        return chatRoomDetailDTOS;
    }

    @Override
    public ChatRoomDetailDTO findChatRoomDetailById(Long roomId) {
        ChatRoom e = chatRoomRepository.findById(roomId).orElseThrow(() -> new BadRequestAlertException("findChatRoomDetailById", "Something wrong!", "Something wrong!"));
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAllByChatRoomId(e.getId());
        ChatRoomUser chatRoomUser = chatRoomUserList.stream().filter(x -> x.getIsClient() == 1).findFirst().orElseThrow(() -> new BadRequestAlertException("Something wrong!", "Something wrong!", "Something wrong!"));
        User client = userRepository.findOneById(chatRoomUser.getUserId()).orElseThrow(() -> new BadRequestAlertException("Something wrong!", "Something wrong!", "Something wrong!"));
        UserDTO clientDTO = new UserDTO(client);
        return new ChatRoomDetailDTO(e, chatRoomUserList.stream().map(ChatRoomUser::getUserId).collect(Collectors.toList()), clientDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChatRoomDTO> findOne(Long id) {
        log.debug("Request to get ChatRoom : {}", id);
        return chatRoomRepository.findById(id)
            .map(chatRoomMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChatRoom : {}", id);
        chatRoomRepository.deleteById(id);
    }

    @Override
    public ChatRoomDTO createRoom(Long userId, Long userAdminId) {
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom());
//        chatRoomUserRepository.save(new ChatRoomUser(chatRoom.getId(),userId, 1));
        chatRoomUserRepository.saveAll(Arrays.asList(new ChatRoomUser(chatRoom.getId(),userId, 1), new ChatRoomUser(chatRoom.getId(),userAdminId, 0)));
        return chatRoomMapper.toDto(chatRoom);
    }
}
