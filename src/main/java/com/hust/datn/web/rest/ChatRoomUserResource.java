package com.hust.datn.web.rest;

import com.hust.datn.service.ChatRoomUserService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.ChatRoomUserDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hust.datn.domain.ChatRoomUser}.
 */
@RestController
@RequestMapping("/api")
public class ChatRoomUserResource {

    private final Logger log = LoggerFactory.getLogger(ChatRoomUserResource.class);

    private static final String ENTITY_NAME = "chatRoomUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatRoomUserService chatRoomUserService;

    public ChatRoomUserResource(ChatRoomUserService chatRoomUserService) {
        this.chatRoomUserService = chatRoomUserService;
    }

    /**
     * {@code POST  /chat-room-users} : Create a new chatRoomUser.
     *
     * @param chatRoomUserDTO the chatRoomUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatRoomUserDTO, or with status {@code 400 (Bad Request)} if the chatRoomUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chat-room-users")
    public ResponseEntity<ChatRoomUserDTO> createChatRoomUser(@RequestBody ChatRoomUserDTO chatRoomUserDTO) throws URISyntaxException {
        log.debug("REST request to save ChatRoomUser : {}", chatRoomUserDTO);
        if (chatRoomUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new chatRoomUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatRoomUserDTO result = chatRoomUserService.save(chatRoomUserDTO);
        return ResponseEntity.created(new URI("/api/chat-room-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chat-room-users} : Updates an existing chatRoomUser.
     *
     * @param chatRoomUserDTO the chatRoomUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatRoomUserDTO,
     * or with status {@code 400 (Bad Request)} if the chatRoomUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatRoomUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chat-room-users")
    public ResponseEntity<ChatRoomUserDTO> updateChatRoomUser(@RequestBody ChatRoomUserDTO chatRoomUserDTO) throws URISyntaxException {
        log.debug("REST request to update ChatRoomUser : {}", chatRoomUserDTO);
        if (chatRoomUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChatRoomUserDTO result = chatRoomUserService.save(chatRoomUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatRoomUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chat-room-users} : get all the chatRoomUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chatRoomUsers in body.
     */
    @GetMapping("/chat-room-users")
    public ResponseEntity<List<ChatRoomUserDTO>> getAllChatRoomUsers(Pageable pageable) {
        log.debug("REST request to get a page of ChatRoomUsers");
        Page<ChatRoomUserDTO> page = chatRoomUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chat-room-users/:id} : get the "id" chatRoomUser.
     *
     * @param id the id of the chatRoomUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatRoomUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chat-room-users/{id}")
    public ResponseEntity<ChatRoomUserDTO> getChatRoomUser(@PathVariable Long id) {
        log.debug("REST request to get ChatRoomUser : {}", id);
        Optional<ChatRoomUserDTO> chatRoomUserDTO = chatRoomUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chatRoomUserDTO);
    }

    /**
     * {@code DELETE  /chat-room-users/:id} : delete the "id" chatRoomUser.
     *
     * @param id the id of the chatRoomUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chat-room-users/{id}")
    public ResponseEntity<Void> deleteChatRoomUser(@PathVariable Long id) {
        log.debug("REST request to delete ChatRoomUser : {}", id);
        chatRoomUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
