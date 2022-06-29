package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class CustOrderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustOrderDTO.class);
        CustOrderDTO custOrderDTO1 = new CustOrderDTO();
        custOrderDTO1.setId(1L);
        CustOrderDTO custOrderDTO2 = new CustOrderDTO();
        assertThat(custOrderDTO1).isNotEqualTo(custOrderDTO2);
        custOrderDTO2.setId(custOrderDTO1.getId());
        assertThat(custOrderDTO1).isEqualTo(custOrderDTO2);
        custOrderDTO2.setId(2L);
        assertThat(custOrderDTO1).isNotEqualTo(custOrderDTO2);
        custOrderDTO1.setId(null);
        assertThat(custOrderDTO1).isNotEqualTo(custOrderDTO2);
    }
}
