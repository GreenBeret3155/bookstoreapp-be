package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class SysModuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysModuleDTO.class);
        SysModuleDTO sysModuleDTO1 = new SysModuleDTO();
        sysModuleDTO1.setId(1L);
        SysModuleDTO sysModuleDTO2 = new SysModuleDTO();
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
        sysModuleDTO2.setId(sysModuleDTO1.getId());
        assertThat(sysModuleDTO1).isEqualTo(sysModuleDTO2);
        sysModuleDTO2.setId(2L);
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
        sysModuleDTO1.setId(null);
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
    }
}
