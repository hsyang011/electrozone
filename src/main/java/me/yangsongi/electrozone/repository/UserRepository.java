package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져옵니다.
    Optional<User> findByName(String name);
    Optional<User> findByNickname(String nickname);

}
