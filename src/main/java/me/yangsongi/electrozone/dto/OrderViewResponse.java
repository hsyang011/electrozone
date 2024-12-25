package me.yangsongi.electrozone.dto;


import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderItem;
import me.yangsongi.electrozone.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record OrderViewResponse(
        User user,
        Integer totalAmount,
        List<OrderItemViewResponse> orderItems,
        LocalDateTime orderDate,
        String status
) {

    public OrderViewResponse(Order order) {
        this(
                order.getUser(),
                order.getTotalAmount(),
                order.getOrderItems().stream()
                        .map(OrderItemViewResponse::new)
                        .toList(),
                order.getOrderDate(),
                order.getStatus()
        );
    }

}
