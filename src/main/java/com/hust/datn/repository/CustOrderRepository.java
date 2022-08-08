package com.hust.datn.repository;

import com.hust.datn.domain.CustOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CustOrder entity.
 */
@Repository
public interface CustOrderRepository extends JpaRepository<CustOrder, Long> {
    List<CustOrder> findAllByUserIdOrderByOrderTimeDesc(Long userId);

    Page<CustOrder> findAllByStateOrderByIdDesc(Integer state, Pageable pageable);

    Page<CustOrder> findAllByOrderByIdDesc(Pageable pageable);
}
