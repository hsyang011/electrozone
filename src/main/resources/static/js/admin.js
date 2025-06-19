let stompClient = null;
let selectedRoomId = null;
let currentSubscription = null;  // 구독 객체 저장용

// 관리자 접속 시 WebSocket 연결
function connectAdmin() {
    const socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect(
        { Authorization: "Bearer " + localStorage.getItem("access_token") },
        () => {
            loadChatRooms();
        },
        (error) => {
            console.error('STOMP 연결 실패:', error);
            // 재연결 로직 필요 시 추가
        }
    );
}

// 채팅방 목록 API 호출 및 UI 렌더링
function loadChatRooms() {
    fetch('/api/admin/chat/rooms', {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
        }
    })
    .then(res => res.json())
    .then(rooms => {
        const listEl = document.getElementById('room-list');
        listEl.innerHTML = '';

        rooms.forEach(room => {
            const li = document.createElement('li');
            li.textContent = `방 번호: ${room.roomId}`;
            li.style.cursor = 'pointer';
            li.onclick = () => selectRoom(room.roomId);
            listEl.appendChild(li);
        });

        // 첫 방 자동 선택 (옵션)
        if (rooms.length > 0 && !selectedRoomId) {
            selectRoom(rooms[0].roomId);
        }
    })
    .catch(err => {
        console.error('채팅방 목록 로드 실패:', err);
    });
}

// 특정 채팅방 선택
function selectRoom(roomId) {
    if (selectedRoomId === roomId) return;

    // 이전 구독 해제
    if (currentSubscription) {
        currentSubscription.unsubscribe();
        currentSubscription = null;
    }

    selectedRoomId = roomId;
    document.getElementById('current-room-name').textContent = `채팅방 ${roomId}`;
    document.getElementById('chat-messages').innerHTML = '';

    // 해당 방 구독
    stompClient.subscribe(`/sub/chat/room/${roomId}`, (messageOutput) => {
        const message = JSON.parse(messageOutput.body);
        const senderType = message.sender === 'admin' ? 'admin' : 'user';
        addMessageToChat(senderType, message.content);
    });

    loadChatHistory(roomId);
}

// 이전 채팅 기록 불러오기
function loadChatHistory(roomId) {
    fetch(`/api/chat/messages?roomId=${roomId}`, {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
        }
    })
    .then(res => res.json())
    .then(messages => {
        messages.forEach(msg => {
            const senderType = msg.sender === 'admin' ? 'admin' : 'user';
            addMessageToChat(senderType, msg.content);
        });
    })
    .catch(err => {
        console.error('채팅 기록 불러오기 실패:', err);
    });
}

// 채팅 메시지 화면에 추가
function addMessageToChat(sender, message) {
    const chatBox = document.getElementById('chat-messages');
    const msgDiv = document.createElement('div');
    msgDiv.className = `message ${sender}`;
    msgDiv.innerText = message;
    chatBox.appendChild(msgDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

// 메시지 전송
function sendAdminMessage(event) {
    event.preventDefault();
    if (!selectedRoomId || !stompClient) return;

    const input = document.getElementById('chat-input');
    const message = input.value.trim();
    if (message === '') return;

    const chatMessage = {
        roomId: selectedRoomId,
        sender: 'admin',
        receiver: 'user',
        content: message,
        type: 'CHAT'
    };

    stompClient.send(
        '/app/chat/message',
        { Authorization: "Bearer " + localStorage.getItem("access_token") },
        JSON.stringify(chatMessage)
    );

    input.value = '';
}

// 초기 연결 시도
window.onload = () => {
    connectAdmin();
};
