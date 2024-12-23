package me.yangsongi.electrozone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderViewController {

    @GetMapping("/checkout")
    public String order() {
        return "checkout";
    }

}
