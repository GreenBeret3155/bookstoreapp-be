package com.hust.datn.repository;

import com.hust.datn.domain.CustOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustOrderRepository extends JpaRepository<CustOrder, Long> {
}
