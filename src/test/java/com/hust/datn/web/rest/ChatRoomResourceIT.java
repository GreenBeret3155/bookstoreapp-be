package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.ChatRoom;
import com.hust.datn.repository.ChatRoomRepository;
import com.hust.datn.service.ChatRoomService;
import com.hust.datn.service.dto.ChatRoomDTO;
import com.hust.datn.service.mapper.ChatRoomMapper;

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
 * Integration tests for the {@link ChatRoomResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChatRoomResourceIT {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChatRoomMockMvc;

    private ChatRoom chatRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatRoom createEntity(EntityManager em) {
        ChatRoom chatRoom = new ChatRoom();
        return chatRoom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatRoom createUpdatedEntity(EntityManager em) {
        ChatRoom chatRoom = new ChatRoom();
        return chatRoom;
    }

    @BeforeEach
    public void initTest() {
        chatRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createChatRoom() throws Exception {
        int databaseSizeBeforeCreate = chatRoomRepository.findAll().size();
        // Create the ChatRoom
        ChatRoomDTO chatRoomDTO = chatRoomMapper.toDto(chatRoom);
        restChatRoomMockMvc.perform(post("/api/chat-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the ChatRoom in the database
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        assertThat(chatRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ChatRoom testChatRoom = chatRoomList.get(chatRoomList.size() - 1);
    }

    @Test
    @Transactional
    public void createChatRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRoomRepository.findAll().size();

        // Create the ChatRoom with an existing ID
        chatRoom.setId(1L);
        ChatRoomDTO chatRoomDTO = chatRoomMapper.toDto(chatRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatRoomMockMvc.perform(post("/api/chat-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChatRoom in the database
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        assertThat(chatRoomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChatRooms() throws Exception {
        // Initialize the database
        chatRoomRepository.saveAndFlush(chatRoom);

        // Get all the chatRoomList
        restChatRoomMockMvc.perform(get("/api/chat-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatRoom.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getChatRoom() throws Exception {
        // Initialize the database
        chatRoomRepository.saveAndFlush(chatRoom);

        // Get the chatRoom
        restChatRoomMockMvc.perform(get("/api/chat-rooms/{id}", chatRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chatRoom.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingChatRoom() throws Exception {
        // Get the chatRoom
        restChatRoomMockMvc.perform(get("/api/chat-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChatRoom() throws Exception {
        // Initialize the database
        chatRoomRepository.saveAndFlush(chatRoom);

        int databaseSizeBeforeUpdate = chatRoomRepository.findAll().size();

        // Update the chatRoom
        ChatRoom updatedChatRoom = chatRoomRepository.findById(chatRoom.getId()).get();
        // Disconnect from session so that the updates on updatedChatRoom are not directly saved in db
        em.detach(updatedChatRoom);
        ChatRoomDTO chatRoomDTO = chatRoomMapper.toDto(updatedChatRoom);

        restChatRoomMockMvc.perform(put("/api/chat-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomDTO)))
            .andExpect(status().isOk());

        // Validate the ChatRoom in the database
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        assertThat(chatRoomList).hasSize(databaseSizeBeforeUpdate);
        ChatRoom testChatRoom = chatRoomList.get(chatRoomList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingChatRoom() throws Exception {
        int databaseSizeBeforeUpdate = chatRoomRepository.findAll().size();

        // Create the ChatRoom
        ChatRoomDTO chatRoomDTO = chatRoomMapper.toDto(chatRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatRoomMockMvc.perform(put("/api/chat-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChatRoom in the database
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        assertThat(chatRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChatRoom() throws Exception {
        // Initialize the database
        chatRoomRepository.saveAndFlush(chatRoom);

        int databaseSizeBeforeDelete = chatRoomRepository.findAll().size();

        // Delete the chatRoom
        restChatRoomMockMvc.perform(delete("/api/chat-rooms/{id}", chatRoom.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        assertThat(chatRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
