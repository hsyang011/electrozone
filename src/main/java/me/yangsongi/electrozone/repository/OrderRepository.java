package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findByUserOrderByOrderDateDesc(User user);

}
