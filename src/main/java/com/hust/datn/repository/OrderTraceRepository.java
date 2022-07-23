package com.hust.datn.repository;

import com.hust.datn.domain.OrderTrace;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderTrace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTraceRepository extends JpaRepository<OrderTrace, Long> {
}
