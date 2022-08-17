package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductAmountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAmountDTO.class);
        ProductAmountDTO productAmountDTO1 = new ProductAmountDTO();
        productAmountDTO1.setId(1L);
        ProductAmountDTO productAmountDTO2 = new ProductAmountDTO();
        assertThat(productAmountDTO1).isNotEqualTo(productAmountDTO2);
        productAmountDTO2.setId(productAmountDTO1.getId());
        assertThat(productAmountDTO1).isEqualTo(productAmountDTO2);
        productAmountDTO2.setId(2L);
        assertThat(productAmountDTO1).isNotEqualTo(productAmountDTO2);
        productAmountDTO1.setId(null);
        assertThat(productAmountDTO1).isNotEqualTo(productAmountDTO2);
    }
}
