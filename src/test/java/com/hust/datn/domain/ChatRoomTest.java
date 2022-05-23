package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ChatRoomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatRoom.class);
        ChatRoom chatRoom1 = new ChatRoom();
        chatRoom1.setId(1L);
        ChatRoom chatRoom2 = new ChatRoom();
        chatRoom2.setId(chatRoom1.getId());
        assertThat(chatRoom1).isEqualTo(chatRoom2);
        chatRoom2.setId(2L);
        assertThat(chatRoom1).isNotEqualTo(chatRoom2);
        chatRoom1.setId(null);
        assertThat(chatRoom1).isNotEqualTo(chatRoom2);
    }
}
