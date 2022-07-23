package com.hust.datn.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hust.datn.web.rest.TestUtil;

public class OrderTraceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTraceDTO.class);
        OrderTraceDTO orderTraceDTO1 = new OrderTraceDTO();
        orderTraceDTO1.setId(1L);
        OrderTraceDTO orderTraceDTO2 = new OrderTraceDTO();
        assertThat(orderTraceDTO1).isNotEqualTo(orderTraceDTO2);
        orderTraceDTO2.setId(orderTraceDTO1.getId());
        assertThat(orderTraceDTO1).isEqualTo(orderTraceDTO2);
        orderTraceDTO2.setId(2L);
        assertThat(orderTraceDTO1).isNotEqualTo(orderTraceDTO2);
        orderTraceDTO1.setId(null);
        assertThat(orderTraceDTO1).isNotEqualTo(orderTraceDTO2);
    }
}
