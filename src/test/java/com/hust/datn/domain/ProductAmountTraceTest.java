package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductAmountTraceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAmountTrace.class);
        ProductAmountTrace productAmountTrace1 = new ProductAmountTrace();
        productAmountTrace1.setId(1L);
        ProductAmountTrace productAmountTrace2 = new ProductAmountTrace();
        productAmountTrace2.setId(productAmountTrace1.getId());
        assertThat(productAmountTrace1).isEqualTo(productAmountTrace2);
        productAmountTrace2.setId(2L);
        assertThat(productAmountTrace1).isNotEqualTo(productAmountTrace2);
        productAmountTrace1.setId(null);
        assertThat(productAmountTrace1).isNotEqualTo(productAmountTrace2);
    }
}
