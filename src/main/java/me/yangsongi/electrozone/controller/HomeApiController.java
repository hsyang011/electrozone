package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Category;
import me.yangsongi.electrozone.dto.HomeViewResponse;
import me.yangsongi.electrozone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeApiController {

    private final ProductService productService;

    @GetMapping("/api/home")
    public ResponseEntity<List<HomeViewResponse>> getProductByCategory(@RequestParam("category")String category) {
        // 카테고리 항목의 인기 상품 12개를 가져와 모델에 저장합니다.
        List<HomeViewResponse> top12PopularProducts = productService.getTop12PopularProducts(Category.valueOf(category)).stream()
                .map(HomeViewResponse::new)
                .toList();

        return ResponseEntity.ok().body(top12PopularProducts);
    }

}
