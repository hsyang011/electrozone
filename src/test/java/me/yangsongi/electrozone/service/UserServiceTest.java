package me.yangsongi.electrozone.service;

import me.yangsongi.electrozone.domain.Role;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.dto.AddUserRequest;
import me.yangsongi.electrozone.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")  // application-test.yml 활성화
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        // 테스트 종료 후 데이터 삭제
        userRepository.deleteAll();
    }

    // save() 검증 테스트
    @DisplayName("save() : 유저 정보를 저장하고, 중복 이메일 및 닉네임을 검증한다")
    @Test
    void save() {
        // given
        AddUserRequest request = new AddUserRequest("user@gmail.com", "testUser", "1234", "01012345678");

        // when
        Long userId = userService.save(request);

        // then
        User savedUser = userRepository.findById(userId).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(request.email());
        assertThat(savedUser.getNickname()).isEqualTo(request.nickname());
    }

    // findById() 검증 테스트
    @DisplayName("findById() : 유저 ID로 유저 정보를 조회할 수 있다")
    @Test
    void findById() {
        // given
        User user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .nickname("testUser")
                .password("password")
                .phone("010-1234-5678")
                .role(Role.USER)
                .build());

        // when
        User foundUser = userService.findById(user.getUserId());

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    // save() 검증 테스트 (이메일 중복)
    @DisplayName("save() : 중복된 이메일이 있을 때 예외가 발생한다")
    @Test
    void save_duplicateEmail() {
        // given
        AddUserRequest request = new AddUserRequest("user@gmail.com", "testUser", "1234", "01012345678");

        // when
        userService.save(request);

        // then
        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이메일입니다.");
    }

    // save() 검증 테스트 (닉네임 중복)
    @DisplayName("save() : 중복된 닉네임이 있을 때 예외가 발생한다")
    @Test
    void save_duplicateNickname() {
        // given
        AddUserRequest request1 = new AddUserRequest("user1@gmail.com", "duplicateNickname", "1234", "01012345678");
        userService.save(request1); // 첫 번째 유저 생성

        AddUserRequest request2 = new AddUserRequest("user2@gmail.com", "duplicateNickname", "1234", "01098765432");

        // when & then
        assertThatThrownBy(() -> userService.save(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 닉네임입니다.");
    }
}
