package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ChatRoomUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatRoomUser.class);
        ChatRoomUser chatRoomUser1 = new ChatRoomUser();
        chatRoomUser1.setId(1L);
        ChatRoomUser chatRoomUser2 = new ChatRoomUser();
        chatRoomUser2.setId(chatRoomUser1.getId());
        assertThat(chatRoomUser1).isEqualTo(chatRoomUser2);
        chatRoomUser2.setId(2L);
        assertThat(chatRoomUser1).isNotEqualTo(chatRoomUser2);
        chatRoomUser1.setId(null);
        assertThat(chatRoomUser1).isNotEqualTo(chatRoomUser2);
    }
}
