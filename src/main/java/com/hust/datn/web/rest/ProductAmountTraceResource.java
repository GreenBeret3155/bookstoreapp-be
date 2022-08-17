package com.hust.datn.web.rest;

import com.hust.datn.service.ProductAmountTraceService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.ProductAmountTraceDTO;

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
 * REST controller for managing {@link com.hust.datn.domain.ProductAmountTrace}.
 */
@RestController
@RequestMapping("/api")
public class ProductAmountTraceResource {

    private final Logger log = LoggerFactory.getLogger(ProductAmountTraceResource.class);

    private static final String ENTITY_NAME = "productAmountTrace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAmountTraceService productAmountTraceService;

    public ProductAmountTraceResource(ProductAmountTraceService productAmountTraceService) {
        this.productAmountTraceService = productAmountTraceService;
    }

    /**
     * {@code POST  /product-amount-traces} : Create a new productAmountTrace.
     *
     * @param productAmountTraceDTO the productAmountTraceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAmountTraceDTO, or with status {@code 400 (Bad Request)} if the productAmountTrace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-amount-traces")
    public ResponseEntity<ProductAmountTraceDTO> createProductAmountTrace(@RequestBody ProductAmountTraceDTO productAmountTraceDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAmountTrace : {}", productAmountTraceDTO);
        if (productAmountTraceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAmountTrace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAmountTraceDTO result = productAmountTraceService.save(productAmountTraceDTO);
        return ResponseEntity.created(new URI("/api/product-amount-traces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-amount-traces} : Updates an existing productAmountTrace.
     *
     * @param productAmountTraceDTO the productAmountTraceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAmountTraceDTO,
     * or with status {@code 400 (Bad Request)} if the productAmountTraceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAmountTraceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-amount-traces")
    public ResponseEntity<ProductAmountTraceDTO> updateProductAmountTrace(@RequestBody ProductAmountTraceDTO productAmountTraceDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAmountTrace : {}", productAmountTraceDTO);
        if (productAmountTraceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAmountTraceDTO result = productAmountTraceService.save(productAmountTraceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAmountTraceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-amount-traces} : get all the productAmountTraces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAmountTraces in body.
     */
    @GetMapping("/product-amount-traces")
    public ResponseEntity<List<ProductAmountTraceDTO>> getAllProductAmountTraces(Pageable pageable) {
        log.debug("REST request to get a page of ProductAmountTraces");
        Page<ProductAmountTraceDTO> page = productAmountTraceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-amount-traces/:id} : get the "id" productAmountTrace.
     *
     * @param id the id of the productAmountTraceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAmountTraceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-amount-traces/{id}")
    public ResponseEntity<ProductAmountTraceDTO> getProductAmountTrace(@PathVariable Long id) {
        log.debug("REST request to get ProductAmountTrace : {}", id);
        Optional<ProductAmountTraceDTO> productAmountTraceDTO = productAmountTraceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAmountTraceDTO);
    }

    /**
     * {@code DELETE  /product-amount-traces/:id} : delete the "id" productAmountTrace.
     *
     * @param id the id of the productAmountTraceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-amount-traces/{id}")
    public ResponseEntity<Void> deleteProductAmountTrace(@PathVariable Long id) {
        log.debug("REST request to delete ProductAmountTrace : {}", id);
        productAmountTraceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
