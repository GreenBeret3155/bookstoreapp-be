package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.ProductSearchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSearch} and its DTO {@link ProductSearchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductSearchMapper extends EntityMapper<ProductSearchDTO, ProductSearch> {



    default ProductSearch fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSearch productSearch = new ProductSearch();
        productSearch.setId(id);
        return productSearch;
    }
}
