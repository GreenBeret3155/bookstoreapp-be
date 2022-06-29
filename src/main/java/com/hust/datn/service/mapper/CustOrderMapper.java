package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.CustOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustOrder} and its DTO {@link CustOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustOrderMapper extends EntityMapper<CustOrderDTO, CustOrder> {



    default CustOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustOrder custOrder = new CustOrder();
        custOrder.setId(id);
        return custOrder;
    }
}
