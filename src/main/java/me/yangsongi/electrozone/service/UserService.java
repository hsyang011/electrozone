package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.domain.Role;
import me.yangsongi.electrozone.dto.AddUserRequest;
import me.yangsongi.electrozone.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String email = request.email();
        String password = encoder.encode(request.password());
        String name = request.name();
        String nickname = request.nickname();
        String phone = request.phone();

        // 이메일 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        // 이름 중복 확인
        Optional<User> checkName = userRepository.findByName(name);
        if (checkName.isPresent()) {
            throw new IllegalArgumentException("이미 가입하셨습니다.");
        }

        // 닉네임 중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 사용자 ROLE 확인 (관리자 기능은 추후에 추가합니다.)
        Role role = Role.USER;

        return userRepository.save(User.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .role(role)
                .build()).getUserId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
