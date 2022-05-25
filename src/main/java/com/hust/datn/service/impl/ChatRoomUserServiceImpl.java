package com.hust.datn.service.impl;

import com.hust.datn.service.ChatRoomUserService;
import com.hust.datn.domain.ChatRoomUser;
import com.hust.datn.repository.ChatRoomUserRepository;
import com.hust.datn.service.dto.ChatRoomUserDTO;
import com.hust.datn.service.mapper.ChatRoomUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChatRoomUser}.
 */
@Service
@Transactional
public class ChatRoomUserServiceImpl implements ChatRoomUserService {

    private final Logger log = LoggerFactory.getLogger(ChatRoomUserServiceImpl.class);

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final ChatRoomUserMapper chatRoomUserMapper;

    public ChatRoomUserServiceImpl(ChatRoomUserRepository chatRoomUserRepository, ChatRoomUserMapper chatRoomUserMapper) {
        this.chatRoomUserRepository = chatRoomUserRepository;
        this.chatRoomUserMapper = chatRoomUserMapper;
    }

    @Override
    public ChatRoomUserDTO save(ChatRoomUserDTO chatRoomUserDTO) {
        log.debug("Request to save ChatRoomUser : {}", chatRoomUserDTO);
        ChatRoomUser chatRoomUser = chatRoomUserMapper.toEntity(chatRoomUserDTO);
        chatRoomUser = chatRoomUserRepository.save(chatRoomUser);
        return chatRoomUserMapper.toDto(chatRoomUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChatRoomUsers");
        return chatRoomUserRepository.findAll(pageable)
            .map(chatRoomUserMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChatRoomUserDTO> findOne(Long id) {
        log.debug("Request to get ChatRoomUser : {}", id);
        return chatRoomUserRepository.findById(id)
            .map(chatRoomUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChatRoomUser : {}", id);
        chatRoomUserRepository.deleteById(id);
    }
}
