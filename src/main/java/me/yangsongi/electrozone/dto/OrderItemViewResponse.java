package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.OrderItem;

public record OrderItemViewResponse(
        String name,
        String imageUrl,
        Integer price,
        Integer quantity
) {

    public OrderItemViewResponse(OrderItem orderItem) {
        this(
                orderItem.getProduct().getName(),
                orderItem.getProduct().getImageUrl(),
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
    }

}
