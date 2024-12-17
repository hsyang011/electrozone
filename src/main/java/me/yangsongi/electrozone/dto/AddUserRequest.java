package me.yangsongi.electrozone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record AddUserRequest(String email, String nickname, String password, String phone) {

}
