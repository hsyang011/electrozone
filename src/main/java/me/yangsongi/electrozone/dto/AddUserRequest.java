package me.yangsongi.electrozone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddUserRequest {

    private String email;
    private String nickname;
    private String password;
    private String phone;

}
