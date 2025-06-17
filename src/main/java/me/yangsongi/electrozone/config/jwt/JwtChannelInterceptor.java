package me.yangsongi.electrozone.config.jwt;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.service.UserService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider TokenProvider;
    private final UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.SEND.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 제거

                if (TokenProvider.validToken(token)) {
                    Long userId = TokenProvider.getUserId(token);
                    User user = userService.findById(userId);

                    // 필요한 권한 정보를 제공하려면 추가적으로 loadUserByUsername 호출 가능
                    Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, List.of());

                    accessor.setUser(auth); // WebSocket에서 Principal로 사용 가능
                } else {
                    throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.");
                }
            } else {
                throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
            }
        }

        return message;
    }
    
}
