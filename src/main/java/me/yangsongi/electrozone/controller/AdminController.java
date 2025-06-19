package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.dto.ChatMessageResponse;
import me.yangsongi.electrozone.dto.ChatRoomResponse;
import me.yangsongi.electrozone.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ChatService chatService;

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    // 관리자용 채팅방 목록 조회
    @GetMapping("/api/admin/chat/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms() {
        List<ChatRoomResponse> rooms = chatService.getAllChatRooms();
        return ResponseEntity.ok().body(rooms);
    }

    // 특정 채팅방 메시지 목록 조회
    @GetMapping("/api/admin/chat/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@RequestParam Long roomId) {
        List<ChatMessageResponse> messages = chatService.getMessagesByRoomId(roomId);
        return ResponseEntity.ok().body(messages);
    }

}
