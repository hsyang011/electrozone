package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
