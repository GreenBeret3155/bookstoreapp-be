package com.hust.datn.web.rest;

import com.hust.datn.service.CustOrderService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.CustOrderDTO;

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
 * REST controller for managing {@link com.hust.datn.domain.CustOrder}.
 */
@RestController
@RequestMapping("/api")
public class CustOrderResource {

    private final Logger log = LoggerFactory.getLogger(CustOrderResource.class);

    private static final String ENTITY_NAME = "custOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustOrderService custOrderService;

    public CustOrderResource(CustOrderService custOrderService) {
        this.custOrderService = custOrderService;
    }

    /**
     * {@code POST  /cust-orders} : Create a new custOrder.
     *
     * @param custOrderDTO the custOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custOrderDTO, or with status {@code 400 (Bad Request)} if the custOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-orders")
    public ResponseEntity<CustOrderDTO> createCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws URISyntaxException {
        log.debug("REST request to save CustOrder : {}", custOrderDTO);
        if (custOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new custOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustOrderDTO result = custOrderService.save(custOrderDTO);
        return ResponseEntity.created(new URI("/api/cust-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-orders} : Updates an existing custOrder.
     *
     * @param custOrderDTO the custOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custOrderDTO,
     * or with status {@code 400 (Bad Request)} if the custOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-orders")
    public ResponseEntity<CustOrderDTO> updateCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws URISyntaxException {
        log.debug("REST request to update CustOrder : {}", custOrderDTO);
        if (custOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustOrderDTO result = custOrderService.save(custOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cust-orders} : get all the custOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custOrders in body.
     */
    @GetMapping("/cust-orders")
    public ResponseEntity<List<CustOrderDTO>> getAllCustOrders(Pageable pageable) {
        log.debug("REST request to get a page of CustOrders");
        Page<CustOrderDTO> page = custOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cust-orders/:id} : get the "id" custOrder.
     *
     * @param id the id of the custOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-orders/{id}")
    public ResponseEntity<CustOrderDTO> getCustOrder(@PathVariable Long id) {
        log.debug("REST request to get CustOrder : {}", id);
        Optional<CustOrderDTO> custOrderDTO = custOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custOrderDTO);
    }

    /**
     * {@code DELETE  /cust-orders/:id} : delete the "id" custOrder.
     *
     * @param id the id of the custOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-orders/{id}")
    public ResponseEntity<Void> deleteCustOrder(@PathVariable Long id) {
        log.debug("REST request to delete CustOrder : {}", id);
        custOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
