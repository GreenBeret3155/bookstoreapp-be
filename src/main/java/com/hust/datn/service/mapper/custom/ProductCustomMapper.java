package com.hust.datn.service.mapper.custom;

import com.hust.datn.domain.Category;
import com.hust.datn.domain.Product;
import com.hust.datn.service.AuthorService;
import com.hust.datn.service.CategoryService;
import com.hust.datn.service.ProductAmountService;
import com.hust.datn.service.dto.AuthorDTO;
import com.hust.datn.service.dto.CategoryDTO;
import com.hust.datn.service.dto.ProductAmountDTO;
import com.hust.datn.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductCustomMapper {
    @Autowired
    AuthorService authorService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductAmountService productAmountService;

    public List<ProductDTO> productsToProductDTOS(List<Product> products) {
        return products.stream()
            .filter(Objects::nonNull)
            .map(this::productToProductDTO)
            .collect(Collectors.toList());
    }

    public ProductDTO productToProductDTO(Product product) {
        AuthorDTO authorDTO ;
        CategoryDTO categoryDTO ;
        if(product.getAuthorId() == null){
            authorDTO = null;
        } else {
            authorDTO = authorService.findOne(product.getAuthorId()).orElse(null);
        }

        if(product.getCategoryId() == null){
            categoryDTO = null;
        } else {
            categoryDTO = categoryService.findOne(product.getCategoryId()).orElse(null);
        }

        ProductAmountDTO productAmountDTO = productAmountService.findOne(product.getId()).orElse(null);

        return new ProductDTO(product, authorDTO, categoryDTO, productAmountDTO);
    }

    public List<Product> productDTOSToProducts(List<ProductDTO> productDTOS) {
        return productDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::productDTOToProduct)
            .collect(Collectors.toList());
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
        return new Product(productDTO,
            productDTO.getAuthor() != null ? productDTO.getAuthor().getId() : null,
            productDTO.getCategory() != null ? productDTO.getCategory().getId() : null);
    }
}
