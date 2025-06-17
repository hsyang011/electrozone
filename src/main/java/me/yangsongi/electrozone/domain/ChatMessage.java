package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long chatMessageId;

    // 메시지가 속한 채팅방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String sender;
    private String receiver;
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 생성자
    public ChatMessage(ChatRoom chatRoom, String sender, String receiver, String content, MessageType type) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.type = type;
    }

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

}
