package com.hust.datn.repository;

import com.hust.datn.domain.ChatRoomUser;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ChatRoomUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    Optional<ChatRoomUser> findByUserIdAndIsClient(Long userId, Integer isClient);
    List<ChatRoomUser> findAllByChatRoomId(Long chatRoomId);
}
