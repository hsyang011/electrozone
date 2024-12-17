package me.yangsongi.electrozone.dto;

import lombok.Getter;
import lombok.Setter;

public record CreateAccessTokenRequest(
        String refreshToken
) { }
