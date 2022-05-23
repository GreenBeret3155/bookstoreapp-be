package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ChatRoomDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatRoomDTO.class);
        ChatRoomDTO chatRoomDTO1 = new ChatRoomDTO();
        chatRoomDTO1.setId(1L);
        ChatRoomDTO chatRoomDTO2 = new ChatRoomDTO();
        assertThat(chatRoomDTO1).isNotEqualTo(chatRoomDTO2);
        chatRoomDTO2.setId(chatRoomDTO1.getId());
        assertThat(chatRoomDTO1).isEqualTo(chatRoomDTO2);
        chatRoomDTO2.setId(2L);
        assertThat(chatRoomDTO1).isNotEqualTo(chatRoomDTO2);
        chatRoomDTO1.setId(null);
        assertThat(chatRoomDTO1).isNotEqualTo(chatRoomDTO2);
    }
}
