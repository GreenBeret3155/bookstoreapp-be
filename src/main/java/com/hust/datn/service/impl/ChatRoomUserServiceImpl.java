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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    @Override
//    @Transactional(readOnly = true)
//    public Optional<ChatRoomUserDTO> findOne(Long userId) {
//        log.debug("Request to get ChatRoomUser : {}", userId);
//        return chatRoomUserRepository.findOne(userId)
//            .map(chatRoomUserMapper::toDto);
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChatRoomUserDTO> findOneByUserIdClient(Long userId) {
        log.debug("Request to get ChatRoomUser by userid : {}", userId);
        return chatRoomUserRepository.findByUserIdAndIsClient(userId, 1)
            .map(chatRoomUserMapper::toDto);
    }

    @Override
    public List<ChatRoomUserDTO> findAllByRoomIdNotClient(Long roomId) {
        return chatRoomUserRepository.findAllByIsClientAndChatRoomId(0, roomId)
            .stream()
            .map(chatRoomUserMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChatRoomUser : {}", id);
        chatRoomUserRepository.deleteById(id);
    }
}
