package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct(Product product);

}
