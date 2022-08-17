package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.ProductAmount;
import com.hust.datn.repository.ProductAmountRepository;
import com.hust.datn.service.ProductAmountService;
import com.hust.datn.service.dto.ProductAmountDTO;
import com.hust.datn.service.mapper.ProductAmountMapper;

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
 * Integration tests for the {@link ProductAmountResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAmountResourceIT {

    @Autowired
    private ProductAmountRepository productAmountRepository;

    @Autowired
    private ProductAmountMapper productAmountMapper;

    @Autowired
    private ProductAmountService productAmountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAmountMockMvc;

    private ProductAmount productAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAmount createEntity(EntityManager em) {
        ProductAmount productAmount = new ProductAmount();
        return productAmount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAmount createUpdatedEntity(EntityManager em) {
        ProductAmount productAmount = new ProductAmount();
        return productAmount;
    }

    @BeforeEach
    public void initTest() {
        productAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAmount() throws Exception {
        int databaseSizeBeforeCreate = productAmountRepository.findAll().size();
        // Create the ProductAmount
        ProductAmountDTO productAmountDTO = productAmountMapper.toDto(productAmount);
        restProductAmountMockMvc.perform(post("/api/product-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAmount in the database
        List<ProductAmount> productAmountList = productAmountRepository.findAll();
        assertThat(productAmountList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAmount testProductAmount = productAmountList.get(productAmountList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAmountRepository.findAll().size();

        // Create the ProductAmount with an existing ID
        productAmount.setId(1L);
        ProductAmountDTO productAmountDTO = productAmountMapper.toDto(productAmount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAmountMockMvc.perform(post("/api/product-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAmount in the database
        List<ProductAmount> productAmountList = productAmountRepository.findAll();
        assertThat(productAmountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductAmounts() throws Exception {
        // Initialize the database
        productAmountRepository.saveAndFlush(productAmount);

        // Get all the productAmountList
        restProductAmountMockMvc.perform(get("/api/product-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAmount.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductAmount() throws Exception {
        // Initialize the database
        productAmountRepository.saveAndFlush(productAmount);

        // Get the productAmount
        restProductAmountMockMvc.perform(get("/api/product-amounts/{id}", productAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAmount.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProductAmount() throws Exception {
        // Get the productAmount
        restProductAmountMockMvc.perform(get("/api/product-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAmount() throws Exception {
        // Initialize the database
        productAmountRepository.saveAndFlush(productAmount);

        int databaseSizeBeforeUpdate = productAmountRepository.findAll().size();

        // Update the productAmount
        ProductAmount updatedProductAmount = productAmountRepository.findById(productAmount.getId()).get();
        // Disconnect from session so that the updates on updatedProductAmount are not directly saved in db
        em.detach(updatedProductAmount);
        ProductAmountDTO productAmountDTO = productAmountMapper.toDto(updatedProductAmount);

        restProductAmountMockMvc.perform(put("/api/product-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAmount in the database
        List<ProductAmount> productAmountList = productAmountRepository.findAll();
        assertThat(productAmountList).hasSize(databaseSizeBeforeUpdate);
        ProductAmount testProductAmount = productAmountList.get(productAmountList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAmount() throws Exception {
        int databaseSizeBeforeUpdate = productAmountRepository.findAll().size();

        // Create the ProductAmount
        ProductAmountDTO productAmountDTO = productAmountMapper.toDto(productAmount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAmountMockMvc.perform(put("/api/product-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAmount in the database
        List<ProductAmount> productAmountList = productAmountRepository.findAll();
        assertThat(productAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAmount() throws Exception {
        // Initialize the database
        productAmountRepository.saveAndFlush(productAmount);

        int databaseSizeBeforeDelete = productAmountRepository.findAll().size();

        // Delete the productAmount
        restProductAmountMockMvc.perform(delete("/api/product-amounts/{id}", productAmount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAmount> productAmountList = productAmountRepository.findAll();
        assertThat(productAmountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
