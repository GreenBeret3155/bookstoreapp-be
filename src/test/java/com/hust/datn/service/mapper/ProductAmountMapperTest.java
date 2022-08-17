package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAmountMapperTest {

    private ProductAmountMapper productAmountMapper;

    @BeforeEach
    public void setUp() {
        productAmountMapper = new ProductAmountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productAmountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productAmountMapper.fromId(null)).isNull();
    }
}
