package com.hust.datn.web.rest;

import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.ChatRoomService;
import com.hust.datn.service.dto.ChatRoomDetailDTO;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.ChatRoomDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
 * REST controller for managing {@link com.hust.datn.domain.ChatRoom}.
 */
@RestController
@RequestMapping("/api")
public class ChatRoomResource {

    private final Logger log = LoggerFactory.getLogger(ChatRoomResource.class);

    private static final String ENTITY_NAME = "chatRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatRoomService chatRoomService;

    @Autowired
    private UserRepository userRepository;

    public ChatRoomResource(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    /**
     * {@code POST  /chat-rooms} : Create a new chatRoom.
     *
     * @param chatRoomDTO the chatRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatRoomDTO, or with status {@code 400 (Bad Request)} if the chatRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chat-rooms")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) throws URISyntaxException {
        log.debug("REST request to save ChatRoom : {}", chatRoomDTO);
        if (chatRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new chatRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatRoomDTO result = chatRoomService.save(chatRoomDTO);
        return ResponseEntity.created(new URI("/api/chat-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chat-rooms} : Updates an existing chatRoom.
     *
     * @param chatRoomDTO the chatRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatRoomDTO,
     * or with status {@code 400 (Bad Request)} if the chatRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chat-rooms")
    public ResponseEntity<ChatRoomDTO> updateChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) throws URISyntaxException {
        log.debug("REST request to update ChatRoom : {}", chatRoomDTO);
        if (chatRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChatRoomDTO result = chatRoomService.save(chatRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chat-rooms} : get all the chatRooms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chatRooms in body.
     */
//    @GetMapping("/chat-rooms")
//    public ResponseEntity<List<ChatRoomDTO>> getAllChatRooms(Pageable pageable) {
//        log.debug("REST request to get a page of ChatRooms");
//        Page<ChatRoomDTO> page = chatRoomService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

    @GetMapping("/chat-rooms")
    public ResponseEntity<List<ChatRoomDetailDTO>> getAllChatRoomsByUser(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
        log.debug("REST request to get a page of ChatRooms");
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"));
        Page<ChatRoomDetailDTO> page = chatRoomService.findAllByUserIdAdmin(userId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/chat-room/{id}")
    public ResponseEntity<ChatRoomDetailDTO> getChatRoomDetail(@PathVariable Long id) {
        log.debug("REST request to get ChatRoomDetail : {}", id);
        ChatRoomDetailDTO chatRoomDTO = chatRoomService.findChatRoomDetailById(id);
        return ResponseEntity.ok().body(chatRoomDTO);
    }

    /**
     * {@code GET  /chat-rooms/:id} : get the "id" chatRoom.
     *
     * @param id the id of the chatRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chat-rooms/{id}")
    public ResponseEntity<ChatRoomDTO> getChatRoom(@PathVariable Long id) {
        log.debug("REST request to get ChatRoom : {}", id);
        Optional<ChatRoomDTO> chatRoomDTO = chatRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chatRoomDTO);
    }

    /**
     * {@code DELETE  /chat-rooms/:id} : delete the "id" chatRoom.
     *
     * @param id the id of the chatRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chat-rooms/{id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long id) {
        log.debug("REST request to delete ChatRoom : {}", id);
        chatRoomService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
