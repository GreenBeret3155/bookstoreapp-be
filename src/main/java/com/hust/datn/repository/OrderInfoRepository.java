package com.hust.datn.repository;

import com.hust.datn.domain.OrderInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    List<OrderInfo> findAllByUserId(Long userId);

    @Modifying
    @Query(value = "update order_info set state = 0 where user_id = :userId", nativeQuery = true)
    void resetStateAllOrderInfos(@Param("userId") Long userId);
}
