package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.ProductSearch} entity.
 */
public class ProductSearchDTO implements Serializable {
    
    private Long id;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSearchDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductSearchDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSearchDTO{" +
            "id=" + getId() +
            "}";
    }
}
