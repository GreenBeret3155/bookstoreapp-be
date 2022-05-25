package com.hust.datn.repository;

import com.hust.datn.domain.ChatRoomUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChatRoomUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
