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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchDTO>> onSearchObject(@RequestParam String q) throws Exception {
        List<ProductSearchDTO> lstData = productSearchService.onSearchObject(q);

        return ResponseEntity.ok().body(lstData);
    }
}
