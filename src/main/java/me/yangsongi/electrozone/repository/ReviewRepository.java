package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct(Product product);

}
