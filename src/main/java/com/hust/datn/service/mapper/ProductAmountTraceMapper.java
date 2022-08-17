package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ProductAmountTraceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAmountTrace} and its DTO {@link ProductAmountTraceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductAmountTraceMapper extends EntityMapper<ProductAmountTraceDTO, ProductAmountTrace> {



    default ProductAmountTrace fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductAmountTrace productAmountTrace = new ProductAmountTrace();
        productAmountTrace.setId(id);
        return productAmountTrace;
    }
}
