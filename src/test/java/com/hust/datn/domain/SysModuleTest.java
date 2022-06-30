package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class SysModuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysModule.class);
        SysModule sysModule1 = new SysModule();
        sysModule1.setId(1L);
        SysModule sysModule2 = new SysModule();
        sysModule2.setId(sysModule1.getId());
        assertThat(sysModule1).isEqualTo(sysModule2);
        sysModule2.setId(2L);
        assertThat(sysModule1).isNotEqualTo(sysModule2);
        sysModule1.setId(null);
        assertThat(sysModule1).isNotEqualTo(sysModule2);
    }
}
