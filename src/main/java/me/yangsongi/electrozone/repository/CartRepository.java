package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
