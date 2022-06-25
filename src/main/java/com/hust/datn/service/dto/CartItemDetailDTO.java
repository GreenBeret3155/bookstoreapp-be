package com.hust.datn.service.dto;

public class CartItemDetailDTO {
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
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer isSelected;

    public CartItemDetailDTO() {
    }

    public CartItemDetailDTO(ProductDTO productDTO, CartItemDTO cartItemDTO) {
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
        this.author = productDTO.getAuthor();
        this.category = productDTO.getCategory();
        this.cartId = cartItemDTO.getCartId();
        this.productId = cartItemDTO.getProductId();
        this.quantity = cartItemDTO.getQuantity();
        this.isSelected = cartItemDTO.getIsSelected();
    }

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

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }
}
