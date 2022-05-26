package com.hust.datn.domain;

import com.hust.datn.service.dto.ProductDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long authorId;
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "source_id")
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "original_price")
    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Basic
    @Column(name = "discount")
    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "discount_rate")
    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    @Basic
    @Column(name = "rating_average")
    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    @Basic
    @Column(name = "favourite_count")
    public Long getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(Long favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    @Basic
    @Column(name = "thumbnail_url")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Basic
    @Column(name = "inventory_status")
    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    @Basic
    @Column(name = "inventory_type")
    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Basic
    @Column(name = "productset_group_name")
    public String getProductsetGroupName() {
        return productsetGroupName;
    }

    public void setProductsetGroupName(String productsetGroupName) {
        this.productsetGroupName = productsetGroupName;
    }

    @Basic
    @Column(name = "all_time_quantity_sold")
    public String getAllTimeQuantitySold() {
        return allTimeQuantitySold;
    }

    public void setAllTimeQuantitySold(String allTimeQuantitySold) {
        this.allTimeQuantitySold = allTimeQuantitySold;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "author_id")
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Product() {
    }

    public Product(ProductDTO productDTO, Long authorId, Long categoryId) {
        this.id = productDTO.getId();
        this.sourceId = productDTO.getSourceId();
        this.name = productDTO.getName();
        this.shortDescription = productDTO.getShortDescription();
        this.price = productDTO.getPrice();
        this.originalPrice = productDTO.getOriginalPrice();
        this.discount = productDTO.getDiscount();
        this.discountRate = productDTO.getDiscountRate();
        this.ratingAverage = productDTO.getRatingAverage();
        this.favouriteCount = productDTO.getFavouriteCount();
        this.thumbnailUrl = productDTO.getThumbnailUrl();
        this.inventoryStatus = productDTO.getInventoryStatus();
        this.inventoryType = productDTO.getInventoryType();
        this.productsetGroupName = productDTO.getProductsetGroupName();
        this.allTimeQuantitySold = productDTO.getAllTimeQuantitySold();
        this.description = productDTO.getDescription();
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            "}";
    }
}
