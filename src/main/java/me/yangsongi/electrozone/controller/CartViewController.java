package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CartViewController {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

}
