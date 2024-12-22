package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Review;
import me.yangsongi.electrozone.dto.AddReviewRequest;
import me.yangsongi.electrozone.dto.AddReviewResponse;
import me.yangsongi.electrozone.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public ResponseEntity<AddReviewResponse> addReview(@RequestBody AddReviewRequest request, Principal principal) {
        Review savedReview = reviewService.addReview(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AddReviewResponse(savedReview));
    }

}
