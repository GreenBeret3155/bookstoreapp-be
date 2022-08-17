package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAmountTraceMapperTest {

    private ProductAmountTraceMapper productAmountTraceMapper;

    @BeforeEach
    public void setUp() {
        productAmountTraceMapper = new ProductAmountTraceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productAmountTraceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productAmountTraceMapper.fromId(null)).isNull();
    }
}
