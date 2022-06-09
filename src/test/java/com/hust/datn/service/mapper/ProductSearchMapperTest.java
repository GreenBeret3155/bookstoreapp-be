package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSearchMapperTest {

    private ProductSearchMapper productSearchMapper;

    @BeforeEach
    public void setUp() {
        productSearchMapper = new ProductSearchMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productSearchMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productSearchMapper.fromId(null)).isNull();
    }
}
