package me.yangsongi.electrozone.config.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.config.jwt.TokenProvider;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.service.RefreshTokenService;
import me.yangsongi.electrozone.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/";

    private final TokenProvider tokenProvider;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        // 세션에 저장된 provider 값을 가져오기 위해 JsonNode와 Session을 가져옵니다.
        JsonNode jsonNode = new ObjectMapper().convertValue(oAuth2User.getAttributes(), JsonNode.class);
        HttpSession session = request.getSession();
        String provider = (String)session.getAttribute("provider");
        session.removeAttribute("provider");

        // provider별로 email을 가져오는 방식이 다르므로 switch문으로 분기합니다.
        String email = switch (provider) {
            case "google" -> jsonNode.get("email").asText();
            case "kakao" -> jsonNode.get("kakao_account").get("email").asText();
            default -> throw new IllegalArgumentException("Unknown provider: " + provider); // 기본값 처리
        };
        User user = userService.findByEmail(email);

        // 리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        refreshTokenService.saveRefreshToken(user.getUserId(), refreshToken);
        refreshTokenService.addRefreshTokenToCookie(request, response, refreshToken);
        // 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);
        // 인증 관련 설정값, 쿠키 제거
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 액세스 토큰을 패스에 추가
    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

}
