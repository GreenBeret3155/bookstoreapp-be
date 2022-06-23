package com.hust.datn.service;

import com.hust.datn.service.dto.ChatMessageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ChatMessage}.
 */
public interface ChatMessageService {

    /**
     * Save a chatMessage.
     *
     * @param chatMessageDTO the entity to save.
     * @return the persisted entity.
     */
    ChatMessageDTO save(ChatMessageDTO chatMessageDTO);

    /**
     * Get all the chatMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatMessageDTO> findMessageByRoom(Long roomId, Pageable pageable);


    /**
     * Get the "id" chatMessage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatMessageDTO> findOne(Long id);

    /**
     * Delete the "id" chatMessage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
