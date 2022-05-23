package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChatRoomMapperTest {

    private ChatRoomMapper chatRoomMapper;

    @BeforeEach
    public void setUp() {
        chatRoomMapper = new ChatRoomMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(chatRoomMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chatRoomMapper.fromId(null)).isNull();
    }
}
