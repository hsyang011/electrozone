package me.yangsongi.electrozone.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.dto.AddUserRequest;
import me.yangsongi.electrozone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(AddUserRequest request) {
        try {
            userService.save(request); // 회원가입 메소드 호출
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다."); // 201 Created 상태 코드 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 중 오류가 발생했습니다."); // 400 Bad Request 상태 코드 반환
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/";
    }


    
}
