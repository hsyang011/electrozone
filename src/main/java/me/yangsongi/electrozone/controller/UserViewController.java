package me.yangsongi.electrozone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String signup() {
        return "join";
    }

    @GetMapping("/find-user")
    public String findUser() {
        return "find-user";
    }

}
