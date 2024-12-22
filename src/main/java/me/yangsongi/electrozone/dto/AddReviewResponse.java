package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.Review;

public record AddReviewResponse(
        Long productId,
        String content
) {
    public AddReviewResponse(Review review) {
        this(
                review.getProduct().getProductId(),
                review.getContent()
        );
    }
}
