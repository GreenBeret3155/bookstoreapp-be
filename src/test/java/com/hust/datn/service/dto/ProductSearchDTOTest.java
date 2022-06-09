package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class ProductSearchDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSearchDTO.class);
        ProductSearchDTO productSearchDTO1 = new ProductSearchDTO();
        productSearchDTO1.setId(1L);
        ProductSearchDTO productSearchDTO2 = new ProductSearchDTO();
        assertThat(productSearchDTO1).isNotEqualTo(productSearchDTO2);
        productSearchDTO2.setId(productSearchDTO1.getId());
        assertThat(productSearchDTO1).isEqualTo(productSearchDTO2);
        productSearchDTO2.setId(2L);
        assertThat(productSearchDTO1).isNotEqualTo(productSearchDTO2);
        productSearchDTO1.setId(null);
        assertThat(productSearchDTO1).isNotEqualTo(productSearchDTO2);
    }
}
