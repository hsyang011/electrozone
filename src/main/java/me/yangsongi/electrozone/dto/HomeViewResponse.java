package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.Product;

public record HomeViewResponse(
    Long productId,
    String imageUrl,
    String name,
    Integer price
) {

    // 외부 Product 객체로부터 값을 받아서 초기화하는 생성자
    public HomeViewResponse(Product product) {
        this(
                product.getProductId(),
                product.getImageUrl(),
                product.getName(),
                product.getPrice()
        );
    }

}
