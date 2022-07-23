package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTraceMapperTest {

    private OrderTraceMapper orderTraceMapper;

    @BeforeEach
    public void setUp() {
        orderTraceMapper = new OrderTraceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orderTraceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderTraceMapper.fromId(null)).isNull();
    }
}
