package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustOrderMapperTest {

    private CustOrderMapper custOrderMapper;

    @BeforeEach
    public void setUp() {
        custOrderMapper = new CustOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(custOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(custOrderMapper.fromId(null)).isNull();
    }
}
