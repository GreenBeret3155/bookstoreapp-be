package com.hust.datn.service;

import com.hust.datn.service.dto.OrderTraceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.OrderTrace}.
 */
public interface OrderTraceService {

    /**
     * Save a orderTrace.
     *
     * @param orderTraceDTO the entity to save.
     * @return the persisted entity.
     */
    OrderTraceDTO save(OrderTraceDTO orderTraceDTO);

    /**
     * Get all the orderTraces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderTraceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orderTrace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderTraceDTO> findOne(Long id);

    /**
     * Delete the "id" orderTrace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
