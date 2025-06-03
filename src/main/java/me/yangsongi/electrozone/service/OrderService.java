package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.*;
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
    private final ProductRepository productRepository;

    public boolean payment(String paymentMethod, int totalPrice) {
        // 결제 처리 로직 (PG사 API 통합 등)
        // 예시로 카드 결제 방식만 처리하는 부분
        if ("kakaopay".equals(paymentMethod)) {
            // 카드 결제 처리 로직
            return true; // 성공 시 true 반환
        }
        return false; // 실패 시 false 반환
    }

    @Transactional
    public Order completeOrder(String email, OrderProcessRequest request, List<CartItem> cartItems) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + email));

        cartItems.forEach(cartItem -> {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("재고가 부족합니다 : " + product.getName());
            }
        });

        Order order = Order.builder()
                .user(user)
                .recipient(request.recipient())
                .paymentMethod(request.paymentMethod())
                .address(request.address())
                .phone(request.phone())
                .orderDate(LocalDateTime.now())
                .payment(request.payment())
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = cartItems.stream()
                        .map((cartItem -> {
                            Product product = cartItem.getProduct();

                            // 재고 감소
                            product.decreaseStock(cartItem.getQuantity());
                            return new OrderItem(cartItem, savedOrder);
                        }))
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

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        if (order.getOrderStatus() == OrderStatus.ORDERED) {
            order.updateStatus(OrderStatus.CANCELLED);
            order.getOrderItems().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                product.increaseStock(orderItem.getQuantity()); // 재고 복구
                productRepository.save(product);
            });
            orderRepository.save(order);

            return order;
        } else if (order.getOrderStatus() == OrderStatus.SHIPPING) {
            throw new IllegalArgumentException("배송 중인 상품은 취소할 수 없습니다.");
        } else {
            throw new IllegalArgumentException("취소할 수 없는 상태입니다.");
        }
    }

    public Order requestReturnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        LocalDateTime now = LocalDateTime.now();
        if (order.getOrderStatus() == OrderStatus.DELIVERED && now.isBefore(order.getOrderDate().plusDays(3))) {
            order.updateStatus(OrderStatus.RETURN_REQUESTED); // 반품 요청 상태로 변경
            orderRepository.save(order);
            return order;
        } else {
            throw new IllegalArgumentException("배송 완료 후 1일 이내에만 반품이 가능합니다.");
        }
    }

}
