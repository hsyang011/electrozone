package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Product;

public record CartViewResponse(
        Long cartItemId,
        String name,
        String imageUrl,
        Integer quantity,
        Integer price,
        Long productId
) {

    // 외부 Product 객체로부터 값을 받아서 초기화하는 생성자
    public CartViewResponse(CartItem cartItem) {
        this(
                cartItem.getCartItemId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getImageUrl(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getProductId()
        );
    }

}
