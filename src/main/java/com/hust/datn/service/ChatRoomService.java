package com.hust.datn.service;

import com.hust.datn.service.dto.ChatRoomDTO;

import com.hust.datn.service.dto.ChatRoomDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.ChatRoom}.
 */
public interface ChatRoomService {

    /**
     * Save a chatRoom.
     *
     * @param chatRoomDTO the entity to save.
     * @return the persisted entity.
     */
    ChatRoomDTO save(ChatRoomDTO chatRoomDTO);

    /**
     * Get all the chatRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatRoomDTO> findAll(Pageable pageable);

    Page<ChatRoomDetailDTO> findAllByUserIdAdmin(Long userId, Pageable pageable);

    ChatRoomDetailDTO findChatRoomDetailById(Long roomId);
    /**
     * Get the "id" chatRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatRoomDTO> findOne(Long id);

    /**
     * Delete the "id" chatRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     *
     * @param userId
     * @return
     */
    ChatRoomDTO createRoom(Long userId, Long userAdminId);
}
