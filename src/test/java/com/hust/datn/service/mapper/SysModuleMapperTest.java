package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysModuleMapperTest {

    private SysModuleMapper sysModuleMapper;

    @BeforeEach
    public void setUp() {
        sysModuleMapper = new SysModuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysModuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysModuleMapper.fromId(null)).isNull();
    }
}
