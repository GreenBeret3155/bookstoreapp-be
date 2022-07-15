package com.hust.datn.service.dto;

import com.hust.datn.domain.Product;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.hust.datn.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;
    private Long sourceId;
    private String name;
    private String shortDescription;
    private Long price;
    private Long originalPrice;
    private Long discount;
    private Long discountRate;
    private Double ratingAverage;
    private Long favouriteCount;
    private String thumbnailUrl;
    private String inventoryStatus;
    private String inventoryType;
    private String productsetGroupName;
    private String allTimeQuantitySold;
    private String description;
    private AuthorDTO author;
    private CategoryDTO category;
    private Long authorId;
    private Long categoryId;
    private Integer status;
    private Instant updateTime;
    private String updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Long getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(Long favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getProductsetGroupName() {
        return productsetGroupName;
    }

    public void setProductsetGroupName(String productsetGroupName) {
        this.productsetGroupName = productsetGroupName;
    }

    public String getAllTimeQuantitySold() {
        return allTimeQuantitySold;
    }

    public void setAllTimeQuantitySold(String allTimeQuantitySold) {
        this.allTimeQuantitySold = allTimeQuantitySold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ProductDTO() {
    }

    public ProductDTO(Product product, AuthorDTO author, CategoryDTO category) {
        this.id = product.getId();
        this.sourceId = product.getSourceId();
        this.name = product.getName();
        this.shortDescription = product.getShortDescription();
        this.price = product.getPrice();
        this.originalPrice = product.getOriginalPrice();
        this.discount = product.getDiscount();
        this.discountRate = product.getDiscountRate();
        this.ratingAverage = product.getRatingAverage();
        this.favouriteCount = product.getFavouriteCount();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.inventoryStatus = product.getInventoryStatus();
        this.inventoryType = product.getInventoryType();
        this.productsetGroupName = product.getProductsetGroupName();
        this.allTimeQuantitySold = product.getAllTimeQuantitySold();
        this.description = product.getDescription();
        this.author = author;
        this.category = category;
        this.status = product.getStatus();
        this.updateTime = product.getUpdateTime();
        this.updateUser = product.getUpdateUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            "}";
    }
}
