package com.hust.datn.service.impl;

import com.hust.datn.service.ChatRoomService;
import com.hust.datn.domain.ChatRoom;
import com.hust.datn.repository.ChatRoomRepository;
import com.hust.datn.service.dto.ChatRoomDTO;
import com.hust.datn.service.mapper.ChatRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChatRoom}.
 */
@Service
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

    private final Logger log = LoggerFactory.getLogger(ChatRoomServiceImpl.class);

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
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
}
