package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class SysRoleModuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysRoleModule.class);
        SysRoleModule sysRoleModule1 = new SysRoleModule();
        sysRoleModule1.setId(1L);
        SysRoleModule sysRoleModule2 = new SysRoleModule();
        sysRoleModule2.setId(sysRoleModule1.getId());
        assertThat(sysRoleModule1).isEqualTo(sysRoleModule2);
        sysRoleModule2.setId(2L);
        assertThat(sysRoleModule1).isNotEqualTo(sysRoleModule2);
        sysRoleModule1.setId(null);
        assertThat(sysRoleModule1).isNotEqualTo(sysRoleModule2);
    }
}
