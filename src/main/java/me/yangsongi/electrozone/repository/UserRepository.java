package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
