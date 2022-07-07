package com.hust.datn.service;

import com.hust.datn.service.dto.OrderInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hust.datn.domain.OrderInfo}.
 */
public interface OrderInfoService {

    /**
     * Save a orderInfo.
     *
     * @param orderInfoDTO the entity to save.
     * @return the persisted entity.
     */
    OrderInfoDTO save(OrderInfoDTO orderInfoDTO);

    /**
     * Reset all states orderInfo.
     *
     * @return the persisted entity.
     */
    OrderInfoDTO setDefaultOrderInfo(Long defaultOrderInfoId, Long userId);

    /**
     * Get all the orderInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the orderInfos.
     *
     * @return the list of entities.
     */
    List<OrderInfoDTO> findAllOrderInfosByUserId(Long userId);


    /**
     * Get the "id" orderInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderInfoDTO> findOne(Long id);

    /**
     * Delete the "id" orderInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
