package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import me.yangsongi.electrozone.dto.AddReviewRequest;
import me.yangsongi.electrozone.dto.ProductViewResponse;
import me.yangsongi.electrozone.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public String product(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.findById(productId);
        List<Review> reviews = productService.findByProduct(product);
        model.addAttribute("product", new ProductViewResponse(product, reviews));

        return "product";
    }

    // 상품에 대한 리뷰 작성
    @PostMapping("/api/products/{productId}/reviews")
    @ResponseBody
    public ResponseEntity<Void> addReview(@PathVariable("productId") Long productId, @RequestBody AddReviewRequest request, Principal principal) {
        productService.addReview(productId, request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
