package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.ChatMessage;
import me.yangsongi.electrozone.domain.ChatRoom;
import me.yangsongi.electrozone.dto.ChatMessageRequest;
import me.yangsongi.electrozone.dto.ChatMessageResponse;
import me.yangsongi.electrozone.dto.ChatRoomResponse;
import me.yangsongi.electrozone.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageRequest request, Principal principal) {
        System.out.println("채팅 보내기 성공!");
        System.out.println("current user: " + request.sender());
        // sender 정보 보완 (인증 정보 활용)
        ChatMessage msg = chatService.saveMessage(
                principal.getName(),
                request.roomId(),
                request.sender(),
                request.receiver(),
                request.content(),
                request.type()
        );

        // 메시지를 구독 중인 사용자에게 전달 (roomId 기준)
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.roomId(), request);
    }

    @PostMapping("/api/chat/room")
    public ResponseEntity<ChatRoomResponse> getOrCreateChatRoom(Principal principal) {
        ChatRoom chatRoom = chatService.getOrCreateChatRoom(principal.getName());
        return ResponseEntity.ok().body(new ChatRoomResponse(chatRoom.getChatRoomId()));
    }

    @GetMapping("/api/chat/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(@RequestParam Long roomId) {
        return ResponseEntity.ok().body(chatService.getMessages(roomId).stream()
                .map(ChatMessageResponse::new)
                .toList());
    }

}
