package com.hust.datn.web.rest;

import com.hust.datn.service.OrderTraceService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.OrderTraceDTO;

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
 * REST controller for managing {@link com.hust.datn.domain.OrderTrace}.
 */
@RestController
@RequestMapping("/api")
public class OrderTraceResource {

    private final Logger log = LoggerFactory.getLogger(OrderTraceResource.class);

    private static final String ENTITY_NAME = "orderTrace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTraceService orderTraceService;

    public OrderTraceResource(OrderTraceService orderTraceService) {
        this.orderTraceService = orderTraceService;
    }

    /**
     * {@code POST  /order-traces} : Create a new orderTrace.
     *
     * @param orderTraceDTO the orderTraceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTraceDTO, or with status {@code 400 (Bad Request)} if the orderTrace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-traces")
    public ResponseEntity<OrderTraceDTO> createOrderTrace(@RequestBody OrderTraceDTO orderTraceDTO) throws URISyntaxException {
        log.debug("REST request to save OrderTrace : {}", orderTraceDTO);
        if (orderTraceDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderTrace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTraceDTO result = orderTraceService.save(orderTraceDTO);
        return ResponseEntity.created(new URI("/api/order-traces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-traces} : Updates an existing orderTrace.
     *
     * @param orderTraceDTO the orderTraceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTraceDTO,
     * or with status {@code 400 (Bad Request)} if the orderTraceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTraceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-traces")
    public ResponseEntity<OrderTraceDTO> updateOrderTrace(@RequestBody OrderTraceDTO orderTraceDTO) throws URISyntaxException {
        log.debug("REST request to update OrderTrace : {}", orderTraceDTO);
        if (orderTraceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTraceDTO result = orderTraceService.save(orderTraceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTraceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-traces} : get all the orderTraces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTraces in body.
     */
    @GetMapping("/order-traces")
    public ResponseEntity<List<OrderTraceDTO>> getAllOrderTraces(Pageable pageable) {
        log.debug("REST request to get a page of OrderTraces");
        Page<OrderTraceDTO> page = orderTraceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-traces/:id} : get the "id" orderTrace.
     *
     * @param id the id of the orderTraceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTraceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-traces/{id}")
    public ResponseEntity<OrderTraceDTO> getOrderTrace(@PathVariable Long id) {
        log.debug("REST request to get OrderTrace : {}", id);
        Optional<OrderTraceDTO> orderTraceDTO = orderTraceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTraceDTO);
    }

    /**
     * {@code DELETE  /order-traces/:id} : delete the "id" orderTrace.
     *
     * @param id the id of the orderTraceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-traces/{id}")
    public ResponseEntity<Void> deleteOrderTrace(@PathVariable Long id) {
        log.debug("REST request to delete OrderTrace : {}", id);
        orderTraceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
