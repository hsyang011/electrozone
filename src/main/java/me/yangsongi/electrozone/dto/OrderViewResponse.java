package me.yangsongi.electrozone.dto;


import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderStatus;
import me.yangsongi.electrozone.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record OrderViewResponse(
        User user,
        Long orderId,
        Integer payment,
        List<OrderItemViewResponse> orderItems,
        LocalDateTime orderDate,
        OrderStatus orderStatus
) {

    public OrderViewResponse(Order order) {
        this(
                order.getUser(),
                order.getOrderId(),
                order.getPayment(),
                order.getOrderItems().stream()
                        .map(OrderItemViewResponse::new)
                        .toList(),
                order.getOrderDate(),
                order.getOrderStatus()
        );
    }

}
