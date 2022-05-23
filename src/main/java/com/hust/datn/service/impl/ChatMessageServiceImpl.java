package com.hust.datn.service.impl;

import com.hust.datn.service.ChatMessageService;
import com.hust.datn.domain.ChatMessage;
import com.hust.datn.repository.ChatMessageRepository;
import com.hust.datn.service.dto.ChatMessageDTO;
import com.hust.datn.service.mapper.ChatMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChatMessage}.
 */
@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final Logger log = LoggerFactory.getLogger(ChatMessageServiceImpl.class);

    private final ChatMessageRepository chatMessageRepository;

    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    @Override
    public ChatMessageDTO save(ChatMessageDTO chatMessageDTO) {
        log.debug("Request to save ChatMessage : {}", chatMessageDTO);
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
        chatMessage = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toDto(chatMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChatMessages");
        return chatMessageRepository.findAll(pageable)
            .map(chatMessageMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChatMessageDTO> findOne(Long id) {
        log.debug("Request to get ChatMessage : {}", id);
        return chatMessageRepository.findById(id)
            .map(chatMessageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChatMessage : {}", id);
        chatMessageRepository.deleteById(id);
    }
}
