package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ChatRoomUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatRoomUserDTO.class);
        ChatRoomUserDTO chatRoomUserDTO1 = new ChatRoomUserDTO();
        chatRoomUserDTO1.setId(1L);
        ChatRoomUserDTO chatRoomUserDTO2 = new ChatRoomUserDTO();
        assertThat(chatRoomUserDTO1).isNotEqualTo(chatRoomUserDTO2);
        chatRoomUserDTO2.setId(chatRoomUserDTO1.getId());
        assertThat(chatRoomUserDTO1).isEqualTo(chatRoomUserDTO2);
        chatRoomUserDTO2.setId(2L);
        assertThat(chatRoomUserDTO1).isNotEqualTo(chatRoomUserDTO2);
        chatRoomUserDTO1.setId(null);
        assertThat(chatRoomUserDTO1).isNotEqualTo(chatRoomUserDTO2);
    }
}
