package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductAmountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAmount.class);
        ProductAmount productAmount1 = new ProductAmount();
        productAmount1.setId(1L);
        ProductAmount productAmount2 = new ProductAmount();
        productAmount2.setId(productAmount1.getId());
        assertThat(productAmount1).isEqualTo(productAmount2);
        productAmount2.setId(2L);
        assertThat(productAmount1).isNotEqualTo(productAmount2);
        productAmount1.setId(null);
        assertThat(productAmount1).isNotEqualTo(productAmount2);
    }
}
