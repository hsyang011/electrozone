package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.ChatMessage;
import me.yangsongi.electrozone.domain.ChatRoom;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.repository.ChatMessageRepository;
import me.yangsongi.electrozone.repository.ChatRoomRepository;
import me.yangsongi.electrozone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    // 채팅방 조회 or 생성
    public ChatRoom getOrCreateChatRoom(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return chatRoomRepository.findByUserId(user.getUserId())
                .orElseGet(() -> chatRoomRepository.save(new ChatRoom(user.getUserId(), "admin")));
    }

    // 메시지 저장
    public ChatMessage saveMessage(String email, Long roomId, String sender, String receiver, String content, ChatMessage.MessageType type) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        ChatMessage message = new ChatMessage(room, sender, receiver, content, type);
        return chatMessageRepository.save(message);
    }

    // 메시지 리스트 조회
    public List<ChatMessage> getMessages(Long roomId) {
        return chatMessageRepository.findByChatRoom_ChatRoomIdOrderByCreatedAtAsc(roomId);
    }

}
