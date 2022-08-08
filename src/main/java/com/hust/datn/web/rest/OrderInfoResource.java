package com.hust.datn.web.rest;

import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.OrderInfoService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.OrderInfoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.hust.datn.domain.OrderInfo}.
 */
@RestController
@RequestMapping("/api")
public class OrderInfoResource {

    private final Logger log = LoggerFactory.getLogger(OrderInfoResource.class);

    private static final String ENTITY_NAME = "orderInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private UserRepository userRepository;

    private final OrderInfoService orderInfoService;

    public OrderInfoResource(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    /**
     * {@code POST  /order-infos} : Create a new orderInfo.
     *
     * @param orderInfoDTO the orderInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderInfoDTO, or with status {@code 400 (Bad Request)} if the orderInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-order-info")
    public ResponseEntity<OrderInfoDTO> createOrderInfo(@RequestBody OrderInfoDTO orderInfoDTO) throws URISyntaxException {
        log.debug("REST request to save OrderInfo : {}", orderInfoDTO);
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("Not found", "Not found", "Not found"));
        if (orderInfoDTO.getId() != null || userId == null) {
            throw new BadRequestAlertException("A new orderInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderInfoDTO.setUserId(userId);
        orderInfoDTO.setState(1);
        OrderInfoDTO result = orderInfoService.save(orderInfoDTO);
        return ResponseEntity.created(new URI("/api/create-order-info/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-infos} : Updates an existing orderInfo.
     *
     * @param orderInfoDTO the orderInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderInfoDTO,
     * or with status {@code 400 (Bad Request)} if the orderInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-infos")
    public ResponseEntity<OrderInfoDTO> updateOrderInfo(@RequestBody OrderInfoDTO orderInfoDTO) throws URISyntaxException {
        log.debug("REST request to update OrderInfo : {}", orderInfoDTO);
        if (orderInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderInfoDTO result = orderInfoService.save(orderInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderInfoDTO.getId().toString()))
            .body(result);
    }

    @PostMapping("/default-order-info")
    public ResponseEntity<OrderInfoDTO> setDefaultOrderInfo(@RequestBody OrderInfoDTO orderInfoDTO) throws URISyntaxException {
        if (orderInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("Not found", "Not found", "Not found"));
        OrderInfoDTO result = orderInfoService.setDefaultOrderInfo(orderInfoDTO.getId(), userId);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /order-info} : get all the orderInfo by userId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderInfos in body.
     */
    @GetMapping("/order-info")
    public ResponseEntity<List<OrderInfoDTO>> getAllOrderInfo() {
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("Not found", "Not found", "Not found"));
        List<OrderInfoDTO> result = orderInfoService.findAllOrderInfosByUserId(userId);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /order-infos} : get all the orderInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderInfos in body.
     */
    @GetMapping("/order-infos")
    public ResponseEntity<List<OrderInfoDTO>> getAllOrderInfos(Pageable pageable) {
        log.debug("REST request to get a page of OrderInfos");
        Page<OrderInfoDTO> page = orderInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-infos/:id} : get the "id" orderInfo.
     *
     * @param id the id of the orderInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-infos/{id}")
    public ResponseEntity<OrderInfoDTO> getOrderInfo(@PathVariable Long id) {
        log.debug("REST request to get OrderInfo : {}", id);
        Optional<OrderInfoDTO> orderInfoDTO = orderInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderInfoDTO);
    }

    /**
     * {@code DELETE  /order-infos/:id} : delete the "id" orderInfo.
     *
     * @param id the id of the orderInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PostMapping("/order-info-delete/{id}")
    public ResponseEntity<List<OrderInfoDTO>> deleteOrderInfo(@PathVariable Long id) {
        log.debug("REST request to delete OrderInfo : {}", id);
        orderInfoService.delete(id);
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("Not found", "Not found", "Not found"));
        List<OrderInfoDTO> result = orderInfoService.findAllOrderInfosByUserId(userId);
        return ResponseEntity.ok().body(result);
    }
}
