package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.ProductCategory;
import me.yangsongi.electrozone.dto.HomeViewResponse;
import me.yangsongi.electrozone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // 최신 상품 6개를 가져와 모델에 저장합니다.
        List<HomeViewResponse> top6LatestProducts = productService.getTop6LatestProducts().stream()
                .map(HomeViewResponse::new)
                .toList();
        model.addAttribute("top6LatestProducts", top6LatestProducts);

        // CPU 항목의 인기 상품 12개를 가져와 모델에 저장합니다.
        List<HomeViewResponse> top12PopularProducts = productService.getTop12PopularProducts(ProductCategory.valueOf("CPU")).stream()
                .map(HomeViewResponse::new)
                .toList();
        model.addAttribute("top12PopularProducts", top12PopularProducts);

        return "index";
    }

    @GetMapping("/api/home")
    @ResponseBody
    public ResponseEntity<List<HomeViewResponse>> getProductByCategory(@RequestParam("category")String category) {
        // 카테고리 항목의 인기 상품 12개를 가져와 모델에 저장합니다.
        List<HomeViewResponse> top12PopularProducts = productService.getTop12PopularProducts(ProductCategory.valueOf(category)).stream()
                .map(HomeViewResponse::new)
                .toList();

        return ResponseEntity.ok().body(top12PopularProducts);
    }

}
