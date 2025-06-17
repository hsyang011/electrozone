package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long chatMessageId,
        Long roomId,
        String sender,
        String receiver,
        String content,
        ChatMessage.MessageType type,
        LocalDateTime createdAt
) {
    // 외부 Product 객체로부터 값을 받아서 초기화하는 생성자
    public ChatMessageResponse(ChatMessage chatMessage) {
        this(
                chatMessage.getChatMessageId(),
                chatMessage.getChatRoom().getChatRoomId(),
                chatMessage.getSender(),
                chatMessage.getReceiver(),
                chatMessage.getContent(),
                chatMessage.getType(),
                chatMessage.getCreatedAt()
        );
    }
}

