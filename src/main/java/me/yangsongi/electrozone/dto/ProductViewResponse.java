package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.Category;
import me.yangsongi.electrozone.domain.Product;

import java.time.LocalDateTime;

public record ProductViewResponse(
        Long productId,
        String name,
        Integer price,
        String specs,
        String imageUrl,
        Integer stock,
        LocalDateTime registeredAt,
        Category category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    // 외부 Product 객체로부터 값을 받아서 초기화하는 생성자
    public ProductViewResponse(Product product) {
        this(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getSpecs(),
                product.getImageUrl(),
                product.getStock(),
                product.getRegisteredAt(),
                product.getCategory(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

}