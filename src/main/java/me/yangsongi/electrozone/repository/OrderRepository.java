package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.OrderStatus;
import me.yangsongi.electrozone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findByUserOrderByOrderDateDesc(User user);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
