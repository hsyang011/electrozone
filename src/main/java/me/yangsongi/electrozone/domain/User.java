package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;  // 사용자 고유 ID입니다.

    @Column(name = "name", nullable = false)
    private String name;  // 사용자 이름입니다.

    @Column(name = "email", nullable = false)
    private String email;  // 사용자 이메일입니다.

    @Column(name = "password", nullable = false)
    private String password;  // 사용자 비밀번호입니다.

    @Column(name = "phone")
    private String phone;  // 사용자 전화번호입니다.

    @Column(name = "role")
    private String role;  // 사용자 역할입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 사용자 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 사용자 수정 시간입니다.

}
