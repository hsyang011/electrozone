package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
