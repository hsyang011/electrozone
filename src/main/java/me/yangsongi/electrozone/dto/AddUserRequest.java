package me.yangsongi.electrozone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddUserRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 20자 이하로 설정해야 합니다.")
        String password,

        @Pattern(regexp = "^[0-9]+$", message = "전화번호는 숫자만 포함해야 합니다.")
        String phone
) { }
