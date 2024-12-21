package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Cart;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
