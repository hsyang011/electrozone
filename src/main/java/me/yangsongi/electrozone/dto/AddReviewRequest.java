package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import me.yangsongi.electrozone.domain.User;

public record AddReviewRequest(
        String content
) {

    public Review toEntity(User user, Product product) {
        return Review.builder()
                .product(product)
                .content(content)
                .user(user)
                .build();
    }

}
