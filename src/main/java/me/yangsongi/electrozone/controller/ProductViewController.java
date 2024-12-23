package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.Review;
import me.yangsongi.electrozone.dto.ProductViewResponse;
import me.yangsongi.electrozone.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public String product(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.findById(productId);
        List<Review> reviews = productService.findByProduct(product);
        model.addAttribute("product", new ProductViewResponse(product, reviews));

        return "product";
    }

}
