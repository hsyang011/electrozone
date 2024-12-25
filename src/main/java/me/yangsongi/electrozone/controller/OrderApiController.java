package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.dto.OrderProcessRequest;
import me.yangsongi.electrozone.dto.OrderProcessResponse;
import me.yangsongi.electrozone.dto.OrderStatusResponse;
import me.yangsongi.electrozone.service.CartService;
import me.yangsongi.electrozone.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/api/checkout")
    public ResponseEntity<OrderProcessResponse> orderProcess(@RequestBody OrderProcessRequest request, Principal principal) {
        try {
            // 사용자 정보를 기반으로 주문 생성
            List<CartItem> cartItems = cartService.getCartItems(principal.getName());
            int totalPrice = request.payment();

            // 결제 서비스에서 실제 결제 처리 (예시로 카드 결제)
            boolean paymentSuccess = orderService.payment(request.paymentMethod(), totalPrice);

            if (paymentSuccess) {
                // 결제 성공 시 주문 완료 처리
                Order order = orderService.completeOrder(principal.getName(), request, cartItems);
                return ResponseEntity.ok().body(new OrderProcessResponse(order.getOrderId(), order.getUser()));
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 주문 취소
    @PostMapping("/api/order/{orderId}/cancel")
    public ResponseEntity<OrderStatusResponse> cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        Order order = orderService.cancelOrder(orderId);

        return ResponseEntity.ok().body(new OrderStatusResponse(order));
    }

    // 반품 처리
    @PostMapping("/api/order/{orderId}/return")
    public ResponseEntity<OrderStatusResponse> returnOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        Order order = orderService.requestReturnOrder(orderId);

        return ResponseEntity.ok().body(new OrderStatusResponse(order));
    }

}
