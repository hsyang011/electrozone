package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.ChatMessage;

public record ChatMessageRequest (
        Long roomId,
        String sender,
        String receiver,
        String content,
        ChatMessage.MessageType type
) { }
