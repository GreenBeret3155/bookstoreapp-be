package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductAmountTraceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAmountTraceDTO.class);
        ProductAmountTraceDTO productAmountTraceDTO1 = new ProductAmountTraceDTO();
        productAmountTraceDTO1.setId(1L);
        ProductAmountTraceDTO productAmountTraceDTO2 = new ProductAmountTraceDTO();
        assertThat(productAmountTraceDTO1).isNotEqualTo(productAmountTraceDTO2);
        productAmountTraceDTO2.setId(productAmountTraceDTO1.getId());
        assertThat(productAmountTraceDTO1).isEqualTo(productAmountTraceDTO2);
        productAmountTraceDTO2.setId(2L);
        assertThat(productAmountTraceDTO1).isNotEqualTo(productAmountTraceDTO2);
        productAmountTraceDTO1.setId(null);
        assertThat(productAmountTraceDTO1).isNotEqualTo(productAmountTraceDTO2);
    }
}
