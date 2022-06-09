package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.ProductSearch;
import com.hust.datn.repository.ProductSearchRepository;
import com.hust.datn.service.ProductSearchService;
import com.hust.datn.service.dto.ProductSearchDTO;
import com.hust.datn.service.mapper.ProductSearchMapper;

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
 * Integration tests for the {@link ProductSearchResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductSearchResourceIT {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Autowired
    private ProductSearchMapper productSearchMapper;

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductSearchMockMvc;

    private ProductSearch productSearch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSearch createEntity(EntityManager em) {
        ProductSearch productSearch = new ProductSearch();
        return productSearch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSearch createUpdatedEntity(EntityManager em) {
        ProductSearch productSearch = new ProductSearch();
        return productSearch;
    }

    @BeforeEach
    public void initTest() {
        productSearch = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSearch() throws Exception {
        int databaseSizeBeforeCreate = productSearchRepository.findAll().size();
        // Create the ProductSearch
        ProductSearchDTO productSearchDTO = productSearchMapper.toDto(productSearch);
        restProductSearchMockMvc.perform(post("/api/product-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSearchDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSearch in the database
        List<ProductSearch> productSearchList = productSearchRepository.findAll();
        assertThat(productSearchList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSearch testProductSearch = productSearchList.get(productSearchList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSearchRepository.findAll().size();

        // Create the ProductSearch with an existing ID
        productSearch.setId(1L);
        ProductSearchDTO productSearchDTO = productSearchMapper.toDto(productSearch);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSearchMockMvc.perform(post("/api/product-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSearchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSearch in the database
        List<ProductSearch> productSearchList = productSearchRepository.findAll();
        assertThat(productSearchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductSearches() throws Exception {
        // Initialize the database
        productSearchRepository.saveAndFlush(productSearch);

        // Get all the productSearchList
        restProductSearchMockMvc.perform(get("/api/product-searches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSearch.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductSearch() throws Exception {
        // Initialize the database
        productSearchRepository.saveAndFlush(productSearch);

        // Get the productSearch
        restProductSearchMockMvc.perform(get("/api/product-searches/{id}", productSearch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSearch.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProductSearch() throws Exception {
        // Get the productSearch
        restProductSearchMockMvc.perform(get("/api/product-searches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSearch() throws Exception {
        // Initialize the database
        productSearchRepository.saveAndFlush(productSearch);

        int databaseSizeBeforeUpdate = productSearchRepository.findAll().size();

        // Update the productSearch
        ProductSearch updatedProductSearch = productSearchRepository.findById(productSearch.getId()).get();
        // Disconnect from session so that the updates on updatedProductSearch are not directly saved in db
        em.detach(updatedProductSearch);
        ProductSearchDTO productSearchDTO = productSearchMapper.toDto(updatedProductSearch);

        restProductSearchMockMvc.perform(put("/api/product-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSearchDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSearch in the database
        List<ProductSearch> productSearchList = productSearchRepository.findAll();
        assertThat(productSearchList).hasSize(databaseSizeBeforeUpdate);
        ProductSearch testProductSearch = productSearchList.get(productSearchList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSearch() throws Exception {
        int databaseSizeBeforeUpdate = productSearchRepository.findAll().size();

        // Create the ProductSearch
        ProductSearchDTO productSearchDTO = productSearchMapper.toDto(productSearch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSearchMockMvc.perform(put("/api/product-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSearchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSearch in the database
        List<ProductSearch> productSearchList = productSearchRepository.findAll();
        assertThat(productSearchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSearch() throws Exception {
        // Initialize the database
        productSearchRepository.saveAndFlush(productSearch);

        int databaseSizeBeforeDelete = productSearchRepository.findAll().size();

        // Delete the productSearch
        restProductSearchMockMvc.perform(delete("/api/product-searches/{id}", productSearch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSearch> productSearchList = productSearchRepository.findAll();
        assertThat(productSearchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
