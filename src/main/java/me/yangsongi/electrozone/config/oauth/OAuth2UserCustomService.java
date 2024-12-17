package me.yangsongi.electrozone.config.oauth;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Role;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        // provider 정보 가져오기
        String provider = userRequest.getClientRegistration().getRegistrationId(); // 예: kakao, google
        saveOrUpdate(user, provider);

        return user;
    }

    // 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User, String provider) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String)attributes.get("email");
        String name = (String)attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(entity -> {
                    updateProviderId(entity, provider, attributes);
                    return entity.update(name);
                })
                .orElseGet(() -> {
                    // 유저가 없으면 새로운 유저 생성
                    User.UserBuilder userBuilder = User.builder()
                            .email(email)
                            .nickname(name)
                            .role(Role.USER)
                            .password(generateRandomPassword());
                    addProviderIdToBuilder(userBuilder, provider, attributes);
                    return userBuilder.build();
                });

        return userRepository.save(user);
    }

    // provider별 ID 업데이트 메서드
    private void updateProviderId(User entity, String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                String kakaoId = (String) attributes.get("id");
                entity.kakaoIdUpdate(kakaoId);
                break;
            case "google":
                String googleId = (String) attributes.get("sub");
                entity.googleIdUpdate(googleId);
                break;
        }
    }

    // provider별 ID를 UserBuilder에 추가하는 메서드
    private void addProviderIdToBuilder(User.UserBuilder userBuilder, String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                String kakaoId = (String) attributes.get("id");
                userBuilder.kakaoId(kakaoId);
                break;
            case "google":
                String googleId = (String) attributes.get("sub");
                userBuilder.googleId(googleId);
                break;
        }
    }

    // 랜덤 비밀번호 생성 메소드
    private String generateRandomPassword() {
        // UUID를 사용해 랜덤 문자열 생성 (길이 12)
        String randomPassword = java.util.UUID.randomUUID().toString().substring(0, 12);
        // 암호화 후 반환
        return new BCryptPasswordEncoder().encode(randomPassword);
    }

}
