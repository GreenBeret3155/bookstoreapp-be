package com.hust.datn.service;

import com.hust.datn.service.dto.ChatRoomUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ChatRoomUser}.
 */
public interface ChatRoomUserService {

    /**
     * Save a chatRoomUser.
     *
     * @param chatRoomUserDTO the entity to save.
     * @return the persisted entity.
     */
    ChatRoomUserDTO save(ChatRoomUserDTO chatRoomUserDTO);

    /**
     * Get all the chatRoomUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatRoomUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chatRoomUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatRoomUserDTO> findOne(Long id);

    /**
     * Delete the "id" chatRoomUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
