package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.OrderTrace;
import com.hust.datn.repository.OrderTraceRepository;
import com.hust.datn.service.OrderTraceService;
import com.hust.datn.service.dto.OrderTraceDTO;
import com.hust.datn.service.mapper.OrderTraceMapper;

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
 * Integration tests for the {@link OrderTraceResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderTraceResourceIT {

    @Autowired
    private OrderTraceRepository orderTraceRepository;

    @Autowired
    private OrderTraceMapper orderTraceMapper;

    @Autowired
    private OrderTraceService orderTraceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTraceMockMvc;

    private OrderTrace orderTrace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTrace createEntity(EntityManager em) {
        OrderTrace orderTrace = new OrderTrace();
        return orderTrace;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTrace createUpdatedEntity(EntityManager em) {
        OrderTrace orderTrace = new OrderTrace();
        return orderTrace;
    }

    @BeforeEach
    public void initTest() {
        orderTrace = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTrace() throws Exception {
        int databaseSizeBeforeCreate = orderTraceRepository.findAll().size();
        // Create the OrderTrace
        OrderTraceDTO orderTraceDTO = orderTraceMapper.toDto(orderTrace);
        restOrderTraceMockMvc.perform(post("/api/order-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTraceDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderTrace in the database
        List<OrderTrace> orderTraceList = orderTraceRepository.findAll();
        assertThat(orderTraceList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTrace testOrderTrace = orderTraceList.get(orderTraceList.size() - 1);
    }

    @Test
    @Transactional
    public void createOrderTraceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTraceRepository.findAll().size();

        // Create the OrderTrace with an existing ID
        orderTrace.setId(1L);
        OrderTraceDTO orderTraceDTO = orderTraceMapper.toDto(orderTrace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTraceMockMvc.perform(post("/api/order-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTraceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTrace in the database
        List<OrderTrace> orderTraceList = orderTraceRepository.findAll();
        assertThat(orderTraceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderTraces() throws Exception {
        // Initialize the database
        orderTraceRepository.saveAndFlush(orderTrace);

        // Get all the orderTraceList
        restOrderTraceMockMvc.perform(get("/api/order-traces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTrace.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderTrace() throws Exception {
        // Initialize the database
        orderTraceRepository.saveAndFlush(orderTrace);

        // Get the orderTrace
        restOrderTraceMockMvc.perform(get("/api/order-traces/{id}", orderTrace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTrace.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOrderTrace() throws Exception {
        // Get the orderTrace
        restOrderTraceMockMvc.perform(get("/api/order-traces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTrace() throws Exception {
        // Initialize the database
        orderTraceRepository.saveAndFlush(orderTrace);

        int databaseSizeBeforeUpdate = orderTraceRepository.findAll().size();

        // Update the orderTrace
        OrderTrace updatedOrderTrace = orderTraceRepository.findById(orderTrace.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTrace are not directly saved in db
        em.detach(updatedOrderTrace);
        OrderTraceDTO orderTraceDTO = orderTraceMapper.toDto(updatedOrderTrace);

        restOrderTraceMockMvc.perform(put("/api/order-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTraceDTO)))
            .andExpect(status().isOk());

        // Validate the OrderTrace in the database
        List<OrderTrace> orderTraceList = orderTraceRepository.findAll();
        assertThat(orderTraceList).hasSize(databaseSizeBeforeUpdate);
        OrderTrace testOrderTrace = orderTraceList.get(orderTraceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTrace() throws Exception {
        int databaseSizeBeforeUpdate = orderTraceRepository.findAll().size();

        // Create the OrderTrace
        OrderTraceDTO orderTraceDTO = orderTraceMapper.toDto(orderTrace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTraceMockMvc.perform(put("/api/order-traces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTraceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTrace in the database
        List<OrderTrace> orderTraceList = orderTraceRepository.findAll();
        assertThat(orderTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTrace() throws Exception {
        // Initialize the database
        orderTraceRepository.saveAndFlush(orderTrace);

        int databaseSizeBeforeDelete = orderTraceRepository.findAll().size();

        // Delete the orderTrace
        restOrderTraceMockMvc.perform(delete("/api/order-traces/{id}", orderTrace.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTrace> orderTraceList = orderTraceRepository.findAll();
        assertThat(orderTraceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
