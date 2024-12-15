package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.RefreshToken;
import me.yangsongi.electrozone.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

}
