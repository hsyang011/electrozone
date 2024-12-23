package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
