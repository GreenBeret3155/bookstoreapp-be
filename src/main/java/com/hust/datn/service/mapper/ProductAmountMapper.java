package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ProductAmountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAmount} and its DTO {@link ProductAmountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductAmountMapper extends EntityMapper<ProductAmountDTO, ProductAmount> {



    default ProductAmount fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductAmount productAmount = new ProductAmount();
        productAmount.setId(id);
        return productAmount;
    }
}
