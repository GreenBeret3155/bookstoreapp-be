package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChatRoomUserMapperTest {

    private ChatRoomUserMapper chatRoomUserMapper;

    @BeforeEach
    public void setUp() {
        chatRoomUserMapper = new ChatRoomUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(chatRoomUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chatRoomUserMapper.fromId(null)).isNull();
    }
}
