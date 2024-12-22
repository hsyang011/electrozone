package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.dto.AddReviewRequest;
import me.yangsongi.electrozone.repository.ProductRepository;
import me.yangsongi.electrozone.repository.ReviewRepository;
import me.yangsongi.electrozone.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public Review addReview(AddReviewRequest request, String email) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("not found : " + request.productId()));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + email));

        return reviewRepository.save(request.toEntity(user, product));
    }

}
