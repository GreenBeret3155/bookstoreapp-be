package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.OrderInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderInfo} and its DTO {@link OrderInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderInfoMapper extends EntityMapper<OrderInfoDTO, OrderInfo> {



    default OrderInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        return orderInfo;
    }
}
