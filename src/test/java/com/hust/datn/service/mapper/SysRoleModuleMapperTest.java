package com.hust.datn.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysRoleModuleMapperTest {

    private SysRoleModuleMapper sysRoleModuleMapper;

    @BeforeEach
    public void setUp() {
        sysRoleModuleMapper = new SysRoleModuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysRoleModuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysRoleModuleMapper.fromId(null)).isNull();
    }
}
