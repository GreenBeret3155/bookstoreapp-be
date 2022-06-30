package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class SysRoleModuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysRoleModuleDTO.class);
        SysRoleModuleDTO sysRoleModuleDTO1 = new SysRoleModuleDTO();
        sysRoleModuleDTO1.setId(1L);
        SysRoleModuleDTO sysRoleModuleDTO2 = new SysRoleModuleDTO();
        assertThat(sysRoleModuleDTO1).isNotEqualTo(sysRoleModuleDTO2);
        sysRoleModuleDTO2.setId(sysRoleModuleDTO1.getId());
        assertThat(sysRoleModuleDTO1).isEqualTo(sysRoleModuleDTO2);
        sysRoleModuleDTO2.setId(2L);
        assertThat(sysRoleModuleDTO1).isNotEqualTo(sysRoleModuleDTO2);
        sysRoleModuleDTO1.setId(null);
        assertThat(sysRoleModuleDTO1).isNotEqualTo(sysRoleModuleDTO2);
    }
}
