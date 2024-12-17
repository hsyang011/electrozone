package me.yangsongi.electrozone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.dto.AddUserRequest;
import me.yangsongi.electrozone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> signup(AddUserRequest request) {
        userService.save(request); // 회원가입 메소드 호출
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created 상태 코드 반환
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK 상태 코드 반환
    }
    
}
