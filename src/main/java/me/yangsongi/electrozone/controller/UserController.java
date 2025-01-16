package me.yangsongi.electrozone.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.dto.AddUserRequest;
import me.yangsongi.electrozone.dto.MyPageViewResponse;
import me.yangsongi.electrozone.dto.OrderViewResponse;
import me.yangsongi.electrozone.service.OrderService;
import me.yangsongi.electrozone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    
    private final UserService userService;
    private final OrderService orderService;

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

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<?> signup(@Valid AddUserRequest request, BindingResult bindingResult) {
        // 유효성 검사 오류가 있는 경우 처리
        if (bindingResult.hasErrors()) {
            // 오류 메시지 리스트 생성
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();

            // 오류 메시지를 클라이언트에 반환
            return ResponseEntity.badRequest().body(errors);
        }

        userService.save(request); // 회원가입 메소드 호출
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created 상태 코드 반환
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK 상태 코드 반환
    }

    @GetMapping("/api/mypage")
    @ResponseBody
    public ResponseEntity<MyPageViewResponse> getAllOrders(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        List<OrderViewResponse> orders = orderService.getAllOrders(principal.getName()).stream()
                .map(OrderViewResponse::new)
                .toList();

        return ResponseEntity.ok().body(new MyPageViewResponse(user, orders));
    }
    
}
