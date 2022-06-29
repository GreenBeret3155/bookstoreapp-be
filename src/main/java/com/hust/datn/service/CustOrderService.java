package com.hust.datn.service;

import com.hust.datn.service.dto.CustOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.CustOrder}.
 */
public interface CustOrderService {

    /**
     * Save a custOrder.
     *
     * @param custOrderDTO the entity to save.
     * @return the persisted entity.
     */
    CustOrderDTO save(CustOrderDTO custOrderDTO);

    /**
     * Get all the custOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" custOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustOrderDTO> findOne(Long id);

    /**
     * Delete the "id" custOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
