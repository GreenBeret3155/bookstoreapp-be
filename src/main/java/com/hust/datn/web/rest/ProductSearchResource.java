package com.hust.datn.web.rest;

import com.hust.datn.service.ProductSearchService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.ProductSearchDTO;

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
 * REST controller for managing {@link com.hust.datn.domain.ProductSearch}.
 */
@RestController
@RequestMapping("/api")
public class ProductSearchResource {

    private final Logger log = LoggerFactory.getLogger(ProductSearchResource.class);

    private static final String ENTITY_NAME = "productSearch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSearchService productSearchService;

    public ProductSearchResource(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    /**
     * {@code POST  /product-searches} : Create a new productSearch.
     *
     * @param productSearchDTO the productSearchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSearchDTO, or with status {@code 400 (Bad Request)} if the productSearch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-searches")
    public ResponseEntity<ProductSearchDTO> createProductSearch(@RequestBody ProductSearchDTO productSearchDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSearch : {}", productSearchDTO);
        if (productSearchDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSearch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSearchDTO result = productSearchService.save(productSearchDTO);
        return ResponseEntity.created(new URI("/api/product-searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-searches} : Updates an existing productSearch.
     *
     * @param productSearchDTO the productSearchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSearchDTO,
     * or with status {@code 400 (Bad Request)} if the productSearchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSearchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-searches")
    public ResponseEntity<ProductSearchDTO> updateProductSearch(@RequestBody ProductSearchDTO productSearchDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSearch : {}", productSearchDTO);
        if (productSearchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSearchDTO result = productSearchService.save(productSearchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSearchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-searches} : get all the productSearches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSearches in body.
     */
    @GetMapping("/product-searches")
    public ResponseEntity<List<ProductSearchDTO>> getAllProductSearches(Pageable pageable) {
        log.debug("REST request to get a page of ProductSearches");
        Page<ProductSearchDTO> page = productSearchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-searches/:id} : get the "id" productSearch.
     *
     * @param id the id of the productSearchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSearchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-searches/{id}")
    public ResponseEntity<ProductSearchDTO> getProductSearch(@PathVariable Long id) {
        log.debug("REST request to get ProductSearch : {}", id);
        Optional<ProductSearchDTO> productSearchDTO = productSearchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSearchDTO);
    }

    /**
     * {@code DELETE  /product-searches/:id} : delete the "id" productSearch.
     *
     * @param id the id of the productSearchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-searches/{id}")
    public ResponseEntity<Void> deleteProductSearch(@PathVariable Long id) {
        log.debug("REST request to delete ProductSearch : {}", id);
        productSearchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
