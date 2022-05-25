package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.ChatRoomUser;
import com.hust.datn.repository.ChatRoomUserRepository;
import com.hust.datn.service.ChatRoomUserService;
import com.hust.datn.service.dto.ChatRoomUserDTO;
import com.hust.datn.service.mapper.ChatRoomUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChatRoomUserResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChatRoomUserResourceIT {

    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @Autowired
    private ChatRoomUserMapper chatRoomUserMapper;

    @Autowired
    private ChatRoomUserService chatRoomUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChatRoomUserMockMvc;

    private ChatRoomUser chatRoomUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatRoomUser createEntity(EntityManager em) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        return chatRoomUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatRoomUser createUpdatedEntity(EntityManager em) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        return chatRoomUser;
    }

    @BeforeEach
    public void initTest() {
        chatRoomUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChatRoomUser() throws Exception {
        int databaseSizeBeforeCreate = chatRoomUserRepository.findAll().size();
        // Create the ChatRoomUser
        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserMapper.toDto(chatRoomUser);
        restChatRoomUserMockMvc.perform(post("/api/chat-room-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ChatRoomUser in the database
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAll();
        assertThat(chatRoomUserList).hasSize(databaseSizeBeforeCreate + 1);
        ChatRoomUser testChatRoomUser = chatRoomUserList.get(chatRoomUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createChatRoomUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRoomUserRepository.findAll().size();

        // Create the ChatRoomUser with an existing ID
        chatRoomUser.setId(1L);
        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserMapper.toDto(chatRoomUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatRoomUserMockMvc.perform(post("/api/chat-room-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChatRoomUser in the database
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAll();
        assertThat(chatRoomUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChatRoomUsers() throws Exception {
        // Initialize the database
        chatRoomUserRepository.saveAndFlush(chatRoomUser);

        // Get all the chatRoomUserList
        restChatRoomUserMockMvc.perform(get("/api/chat-room-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatRoomUser.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getChatRoomUser() throws Exception {
        // Initialize the database
        chatRoomUserRepository.saveAndFlush(chatRoomUser);

        // Get the chatRoomUser
        restChatRoomUserMockMvc.perform(get("/api/chat-room-users/{id}", chatRoomUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chatRoomUser.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingChatRoomUser() throws Exception {
        // Get the chatRoomUser
        restChatRoomUserMockMvc.perform(get("/api/chat-room-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChatRoomUser() throws Exception {
        // Initialize the database
        chatRoomUserRepository.saveAndFlush(chatRoomUser);

        int databaseSizeBeforeUpdate = chatRoomUserRepository.findAll().size();

        // Update the chatRoomUser
        ChatRoomUser updatedChatRoomUser = chatRoomUserRepository.findById(chatRoomUser.getId()).get();
        // Disconnect from session so that the updates on updatedChatRoomUser are not directly saved in db
        em.detach(updatedChatRoomUser);
        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserMapper.toDto(updatedChatRoomUser);

        restChatRoomUserMockMvc.perform(put("/api/chat-room-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomUserDTO)))
            .andExpect(status().isOk());

        // Validate the ChatRoomUser in the database
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAll();
        assertThat(chatRoomUserList).hasSize(databaseSizeBeforeUpdate);
        ChatRoomUser testChatRoomUser = chatRoomUserList.get(chatRoomUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingChatRoomUser() throws Exception {
        int databaseSizeBeforeUpdate = chatRoomUserRepository.findAll().size();

        // Create the ChatRoomUser
        ChatRoomUserDTO chatRoomUserDTO = chatRoomUserMapper.toDto(chatRoomUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatRoomUserMockMvc.perform(put("/api/chat-room-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChatRoomUser in the database
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAll();
        assertThat(chatRoomUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChatRoomUser() throws Exception {
        // Initialize the database
        chatRoomUserRepository.saveAndFlush(chatRoomUser);

        int databaseSizeBeforeDelete = chatRoomUserRepository.findAll().size();

        // Delete the chatRoomUser
        restChatRoomUserMockMvc.perform(delete("/api/chat-room-users/{id}", chatRoomUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAll();
        assertThat(chatRoomUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
