package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.dto.HomeViewResponse;
import me.yangsongi.electrozone.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<HomeViewResponse> top6LatestProducts = productService.findTop6ByOrderByRegisteredAtDesc().stream()
                .map(product -> new HomeViewResponse(product.getProductId(), product.getImageUrl(), product.getName(), product.getPrice()))
                .toList();
        model.addAttribute("top6LatestProducts", top6LatestProducts);

        return "index";
    }

}
