package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductSearchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSearch.class);
        ProductSearch productSearch1 = new ProductSearch();
        productSearch1.setId(1L);
        ProductSearch productSearch2 = new ProductSearch();
        productSearch2.setId(productSearch1.getId());
        assertThat(productSearch1).isEqualTo(productSearch2);
        productSearch2.setId(2L);
        assertThat(productSearch1).isNotEqualTo(productSearch2);
        productSearch1.setId(null);
        assertThat(productSearch1).isNotEqualTo(productSearch2);
    }
}
