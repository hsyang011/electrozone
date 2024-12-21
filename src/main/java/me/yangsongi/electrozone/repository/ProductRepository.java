package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop6ByOrderByRegisteredAtDesc();

}
