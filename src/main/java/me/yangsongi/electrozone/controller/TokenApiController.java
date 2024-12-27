package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.dto.CreateAccessTokenRequest;
import me.yangsongi.electrozone.dto.CreateAccessTokenResponse;
import me.yangsongi.electrozone.service.RefreshTokenService;
import me.yangsongi.electrozone.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.refreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity<Void> deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }

}
