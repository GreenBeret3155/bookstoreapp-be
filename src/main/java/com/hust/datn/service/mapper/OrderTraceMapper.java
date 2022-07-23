package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.OrderTraceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderTrace} and its DTO {@link OrderTraceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderTraceMapper extends EntityMapper<OrderTraceDTO, OrderTrace> {



    default OrderTrace fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderTrace orderTrace = new OrderTrace();
        orderTrace.setId(id);
        return orderTrace;
    }
}
