package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class CustOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustOrder.class);
        CustOrder custOrder1 = new CustOrder();
        custOrder1.setId(1L);
        CustOrder custOrder2 = new CustOrder();
        custOrder2.setId(custOrder1.getId());
        assertThat(custOrder1).isEqualTo(custOrder2);
        custOrder2.setId(2L);
        assertThat(custOrder1).isNotEqualTo(custOrder2);
        custOrder1.setId(null);
        assertThat(custOrder1).isNotEqualTo(custOrder2);
    }
}
