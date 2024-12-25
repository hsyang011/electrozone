package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderItem;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.dto.OrderProcessRequest;
import me.yangsongi.electrozone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public boolean payment(String paymentMethod, int totalPrice) {
        // 결제 처리 로직 (PG사 API 통합 등)
        // 예시로 카드 결제 방식만 처리하는 부분
        if ("kakao-pay".equals(paymentMethod)) {
            // 카드 결제 처리 로직
            return true; // 성공 시 true 반환
        }
        return false; // 실패 시 false 반환
    }

    @Transactional
    public Order completeOrder(String email, OrderProcessRequest request, List<CartItem> cartItems) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + email));

        Order order = Order.builder()
                .user(user)
                .recipient(request.recipient())
                .paymentMethod(request.paymentMethod())
                .address(request.address())
                .phone(request.phone())
                .orderDate(LocalDateTime.now())
                .totalAmount(cartItems.stream().mapToInt(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum())
                .status("결제완료")
                .build();

        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = cartItems.stream()
                        .map((cartItem -> new OrderItem(cartItem, savedOrder)))
                        .toList();
        orderItemRepository.saveAll(orderItems);
        cartRepository.deleteByUser(user);

        return savedOrder;
    }

    public List<Order> getAllOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user)
                .orElseThrow(() -> new IllegalArgumentException("주문 내역을 찾을 수 없습니다."));

        return orders;
    }

}
