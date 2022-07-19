package com.hust.datn.repository;

import com.hust.datn.domain.ChatRoom;

import com.hust.datn.domain.ChatRoomDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ChatRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query(value = "select cr.* from chat_room cr\n" +
        "join chat_room_user cru on cru.chat_room_id = cr.id \n" +
        "where cru.user_id = :userId\n",
        countQuery = "select count(cr.id) from chat_room cr\n" +
            "join chat_room_user cru on cru.chat_room_id = cr.id \n" +
            "where cru.user_id = :userId\n",
        nativeQuery = true)
    Page<ChatRoom> findAllChatRoomByAdmin(@Param(value = "userId") Long userAdminId,Pageable pageable);
}
