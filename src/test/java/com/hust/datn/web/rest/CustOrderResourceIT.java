package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.CustOrder;
import com.hust.datn.repository.CustOrderRepository;
import com.hust.datn.service.CustOrderService;
import com.hust.datn.service.dto.CustOrderDTO;
import com.hust.datn.service.mapper.CustOrderMapper;

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
 * Integration tests for the {@link CustOrderResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustOrderResourceIT {

    @Autowired
    private CustOrderRepository custOrderRepository;

    @Autowired
    private CustOrderMapper custOrderMapper;

    @Autowired
    private CustOrderService custOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustOrderMockMvc;

    private CustOrder custOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustOrder createEntity(EntityManager em) {
        CustOrder custOrder = new CustOrder();
        return custOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustOrder createUpdatedEntity(EntityManager em) {
        CustOrder custOrder = new CustOrder();
        return custOrder;
    }

    @BeforeEach
    public void initTest() {
        custOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustOrder() throws Exception {
        int databaseSizeBeforeCreate = custOrderRepository.findAll().size();
        // Create the CustOrder
        CustOrderDTO custOrderDTO = custOrderMapper.toDto(custOrder);
        restCustOrderMockMvc.perform(post("/api/cust-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CustOrder testCustOrder = custOrderList.get(custOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void createCustOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custOrderRepository.findAll().size();

        // Create the CustOrder with an existing ID
        custOrder.setId(1L);
        CustOrderDTO custOrderDTO = custOrderMapper.toDto(custOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustOrderMockMvc.perform(post("/api/cust-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustOrders() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        // Get all the custOrderList
        restCustOrderMockMvc.perform(get("/api/cust-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custOrder.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        // Get the custOrder
        restCustOrderMockMvc.perform(get("/api/cust-orders/{id}", custOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custOrder.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustOrder() throws Exception {
        // Get the custOrder
        restCustOrderMockMvc.perform(get("/api/cust-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        int databaseSizeBeforeUpdate = custOrderRepository.findAll().size();

        // Update the custOrder
        CustOrder updatedCustOrder = custOrderRepository.findById(custOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCustOrder are not directly saved in db
        em.detach(updatedCustOrder);
        CustOrderDTO custOrderDTO = custOrderMapper.toDto(updatedCustOrder);

        restCustOrderMockMvc.perform(put("/api/cust-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custOrderDTO)))
            .andExpect(status().isOk());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeUpdate);
        CustOrder testCustOrder = custOrderList.get(custOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCustOrder() throws Exception {
        int databaseSizeBeforeUpdate = custOrderRepository.findAll().size();

        // Create the CustOrder
        CustOrderDTO custOrderDTO = custOrderMapper.toDto(custOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustOrderMockMvc.perform(put("/api/cust-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        int databaseSizeBeforeDelete = custOrderRepository.findAll().size();

        // Delete the custOrder
        restCustOrderMockMvc.perform(delete("/api/cust-orders/{id}", custOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
