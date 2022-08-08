package com.hust.datn.repository;

import com.hust.datn.domain.OrderTrace;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderTrace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTraceRepository extends JpaRepository<OrderTrace, Long> {
    List<OrderTrace> findAllByOrderIdOrderByUpdateTimeDesc(Long orderId);
}
