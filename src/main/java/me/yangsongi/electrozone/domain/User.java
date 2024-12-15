package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;  // 사용자 고유 ID입니다.

    @Column(name = "nickname", nullable = false)
    private String nickname;  // 사용자 이름입니다.

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

    @Builder
    public User(String email, String nickname, String password, String phone) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환 (고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }

    // 사용자 이름 변경
    public User update(String nickname) {
        this.nickname = nickname;

        return this;
    }

}
