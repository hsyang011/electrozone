package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
