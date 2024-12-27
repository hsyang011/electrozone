package me.yangsongi.electrozone.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.config.jwt.TokenProvider;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class FormSuccessHandler implements AuthenticationSuccessHandler {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(1);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(30);

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증 성공한 사용자 정보 가져오기
        User user = (User)authentication.getPrincipal();

        // 리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        refreshTokenService.saveRefreshToken(user.getUserId(), refreshToken);
        refreshTokenService.addRefreshTokenToCookie(request, response, refreshToken);

        // 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);

        response.setHeader(AUTHORIZATION_HEADER, accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
