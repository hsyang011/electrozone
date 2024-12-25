package me.yangsongi.electrozone.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderStatus;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.repository.OrderRepository;
import me.yangsongi.electrozone.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void updateOrderStatus() {
        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.ORDERED);
        LocalDateTime now = LocalDateTime.now();

        orders.forEach(order -> {
            if (order.getOrderDate().plusDays(1).isBefore(now)) {
                order.updateStatus(OrderStatus.SHIPPING); // 배송 중 상태로 변경
            }
            if (order.getOrderDate().plusDays(2).isBefore(now)) {
                order.updateStatus(OrderStatus.DELIVERED); // 배송 완료 상태로 변경
            }
        });

        orderRepository.saveAll(orders);
    }

    // 반품 승인
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void processReturnRequests() {
        List<Order> returnRequests = orderRepository.findByOrderStatus(OrderStatus.RETURN_REQUESTED);
        LocalDateTime now = LocalDateTime.now();

        returnRequests.forEach(order -> {
            if (order.getOrderDate().plusDays(3).isBefore(now)) {
                order.updateStatus(OrderStatus.RETURNED); // 반품 완료 상태로 변경
                order.getOrderItems().forEach(orderItem -> {
                    Product product = orderItem.getProduct();
                    product.increaseStock(orderItem.getQuantity()); // 재고 복구
                    productRepository.save(product);
                });
            }
        });
        orderRepository.saveAll(returnRequests);
    }

}
