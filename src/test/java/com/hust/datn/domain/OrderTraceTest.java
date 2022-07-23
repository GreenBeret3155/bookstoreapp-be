package com.hust.datn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class OrderTraceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTrace.class);
        OrderTrace orderTrace1 = new OrderTrace();
        orderTrace1.setId(1L);
        OrderTrace orderTrace2 = new OrderTrace();
        orderTrace2.setId(orderTrace1.getId());
        assertThat(orderTrace1).isEqualTo(orderTrace2);
        orderTrace2.setId(2L);
        assertThat(orderTrace1).isNotEqualTo(orderTrace2);
        orderTrace1.setId(null);
        assertThat(orderTrace1).isNotEqualTo(orderTrace2);
    }
}
