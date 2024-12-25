package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderStatus;

public record OrderStatusResponse(
        Long orderId,
        OrderStatus orderStatus
) {

    public OrderStatusResponse(Order order) {
        this(
                order.getOrderId(),
                order.getOrderStatus()
        );
    }

}
