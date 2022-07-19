package com.hust.datn.repository;

import com.hust.datn.domain.ChatRoom;
import com.hust.datn.domain.ChatRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomDetailRepository{
    List<?> findAllRoomsAdmin(Long userId);
}
