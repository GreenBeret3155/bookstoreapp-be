package com.hust.datn.web.rest;

import com.hust.datn.service.ProductAmountService;
import com.hust.datn.service.dto.ResponseMessageDTO;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.ProductAmountDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import javassist.NotFoundException;
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
 * REST controller for managing {@link com.hust.datn.domain.ProductAmount}.
 */
@RestController
@RequestMapping("/api")
public class ProductAmountResource {

    private final Logger log = LoggerFactory.getLogger(ProductAmountResource.class);

    private static final String ENTITY_NAME = "productAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAmountService productAmountService;

    public ProductAmountResource(ProductAmountService productAmountService) {
        this.productAmountService = productAmountService;
    }

    /**
     * {@code POST  /product-amounts} : Create a new productAmount.
     *
     * @param productAmountDTO the productAmountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAmountDTO, or with status {@code 400 (Bad Request)} if the productAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-amounts")
    public ResponseEntity<ProductAmountDTO> createProductAmount(@RequestBody ProductAmountDTO productAmountDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAmount : {}", productAmountDTO);
        if (productAmountDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAmountDTO result = productAmountService.save(productAmountDTO);
        return ResponseEntity.created(new URI("/api/product-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-amounts} : Updates an existing productAmount.
     *
     * @param productAmountDTO the productAmountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAmountDTO,
     * or with status {@code 400 (Bad Request)} if the productAmountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAmountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-amounts")
    public ResponseEntity<ProductAmountDTO> updateProductAmount(@RequestBody ProductAmountDTO productAmountDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAmount : {}", productAmountDTO);
        if (productAmountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAmountDTO result = productAmountService.save(productAmountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAmountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-amounts} : get all the productAmounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAmounts in body.
     */
    @GetMapping("/product-amounts")
    public ResponseEntity<List<ProductAmountDTO>> getAllProductAmounts(Pageable pageable) {
        log.debug("REST request to get a page of ProductAmounts");
        Page<ProductAmountDTO> page = productAmountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-amounts/:id} : get the "id" productAmount.
     *
     * @param id the id of the productAmountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAmountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-amounts/{id}")
    public ResponseEntity<ProductAmountDTO> getProductAmount(@PathVariable Long id) throws NotFoundException {
        log.debug("REST request to get ProductAmount : {}", id);
        ProductAmountDTO productAmountDTO = productAmountService.findOneByPId(id);
        if(productAmountDTO == null){
            throw new NotFoundException("ProductAmount" + id);
        }
        return ResponseEntity.ok().body(productAmountDTO);
    }

    @PostMapping("/change-amount")
    public ResponseEntity<?> changeProductAmount(@RequestBody ProductAmountDTO productAmountDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAmount : {}", productAmountDTO);
        if (productAmountDTO.getProductId() == null ||
            productAmountDTO.getAvailable() == null
        ) {
            throw new BadRequestAlertException("null", ENTITY_NAME, "null");
        }
        if(productAmountDTO.getAmount() == null ||
            productAmountDTO.getAmount() == 0
        ){
            productAmountService.changeAvailable(productAmountDTO);
        } else {
            productAmountService.changeAmount(productAmountDTO);
        }
        return ResponseEntity.ok().body(new ResponseMessageDTO(1, "Cập nhật thành công"));
    }

    /**
     * {@code DELETE  /product-amounts/:id} : delete the "id" productAmount.
     *
     * @param id the id of the productAmountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-amounts/{id}")
    public ResponseEntity<Void> deleteProductAmount(@PathVariable Long id) {
        log.debug("REST request to delete ProductAmount : {}", id);
        productAmountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
