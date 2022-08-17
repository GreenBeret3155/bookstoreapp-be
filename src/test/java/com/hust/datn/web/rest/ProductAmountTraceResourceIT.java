package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.ProductAmountTrace;
import com.hust.datn.repository.ProductAmountTraceRepository;
import com.hust.datn.service.ProductAmountTraceService;
import com.hust.datn.service.dto.ProductAmountTraceDTO;
import com.hust.datn.service.mapper.ProductAmountTraceMapper;

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
 * Integration tests for the {@link ProductAmountTraceResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAmountTraceResourceIT {

    @Autowired
    private ProductAmountTraceRepository productAmountTraceRepository;

    @Autowired
    private ProductAmountTraceMapper productAmountTraceMapper;

    @Autowired
    private ProductAmountTraceService productAmountTraceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAmountTraceMockMvc;

    private ProductAmountTrace productAmountTrace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAmountTrace createEntity(EntityManager em) {
        ProductAmountTrace productAmountTrace = new ProductAmountTrace();
        return productAmountTrace;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAmountTrace createUpdatedEntity(EntityManager em) {
        ProductAmountTrace productAmountTrace = new ProductAmountTrace();
        return productAmountTrace;
    }

    @BeforeEach
    public void initTest() {
        productAmountTrace = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAmountTrace() throws Exception {
        int databaseSizeBeforeCreate = productAmountTraceRepository.findAll().size();
        // Create the ProductAmountTrace
        ProductAmountTraceDTO productAmountTraceDTO = productAmountTraceMapper.toDto(productAmountTrace);
        restProductAmountTraceMockMvc.perform(post("/api/product-amount-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountTraceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAmountTrace in the database
        List<ProductAmountTrace> productAmountTraceList = productAmountTraceRepository.findAll();
        assertThat(productAmountTraceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAmountTrace testProductAmountTrace = productAmountTraceList.get(productAmountTraceList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductAmountTraceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAmountTraceRepository.findAll().size();

        // Create the ProductAmountTrace with an existing ID
        productAmountTrace.setId(1L);
        ProductAmountTraceDTO productAmountTraceDTO = productAmountTraceMapper.toDto(productAmountTrace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAmountTraceMockMvc.perform(post("/api/product-amount-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountTraceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAmountTrace in the database
        List<ProductAmountTrace> productAmountTraceList = productAmountTraceRepository.findAll();
        assertThat(productAmountTraceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductAmountTraces() throws Exception {
        // Initialize the database
        productAmountTraceRepository.saveAndFlush(productAmountTrace);

        // Get all the productAmountTraceList
        restProductAmountTraceMockMvc.perform(get("/api/product-amount-traces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAmountTrace.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductAmountTrace() throws Exception {
        // Initialize the database
        productAmountTraceRepository.saveAndFlush(productAmountTrace);

        // Get the productAmountTrace
        restProductAmountTraceMockMvc.perform(get("/api/product-amount-traces/{id}", productAmountTrace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAmountTrace.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProductAmountTrace() throws Exception {
        // Get the productAmountTrace
        restProductAmountTraceMockMvc.perform(get("/api/product-amount-traces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAmountTrace() throws Exception {
        // Initialize the database
        productAmountTraceRepository.saveAndFlush(productAmountTrace);

        int databaseSizeBeforeUpdate = productAmountTraceRepository.findAll().size();

        // Update the productAmountTrace
        ProductAmountTrace updatedProductAmountTrace = productAmountTraceRepository.findById(productAmountTrace.getId()).get();
        // Disconnect from session so that the updates on updatedProductAmountTrace are not directly saved in db
        em.detach(updatedProductAmountTrace);
        ProductAmountTraceDTO productAmountTraceDTO = productAmountTraceMapper.toDto(updatedProductAmountTrace);

        restProductAmountTraceMockMvc.perform(put("/api/product-amount-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountTraceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAmountTrace in the database
        List<ProductAmountTrace> productAmountTraceList = productAmountTraceRepository.findAll();
        assertThat(productAmountTraceList).hasSize(databaseSizeBeforeUpdate);
        ProductAmountTrace testProductAmountTrace = productAmountTraceList.get(productAmountTraceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAmountTrace() throws Exception {
        int databaseSizeBeforeUpdate = productAmountTraceRepository.findAll().size();

        // Create the ProductAmountTrace
        ProductAmountTraceDTO productAmountTraceDTO = productAmountTraceMapper.toDto(productAmountTrace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAmountTraceMockMvc.perform(put("/api/product-amount-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountTraceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAmountTrace in the database
        List<ProductAmountTrace> productAmountTraceList = productAmountTraceRepository.findAll();
        assertThat(productAmountTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAmountTrace() throws Exception {
        // Initialize the database
        productAmountTraceRepository.saveAndFlush(productAmountTrace);

        int databaseSizeBeforeDelete = productAmountTraceRepository.findAll().size();

        // Delete the productAmountTrace
        restProductAmountTraceMockMvc.perform(delete("/api/product-amount-traces/{id}", productAmountTrace.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAmountTrace> productAmountTraceList = productAmountTraceRepository.findAll();
        assertThat(productAmountTraceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
