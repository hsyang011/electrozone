let selectedRoomId = null;
let currentSubscription = null;

// 관리자 접속 시 WebSocket 연결
function connectAdmin() {
    const socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect(
        { Authorization: "Bearer " + localStorage.getItem("access_token") },
        onConnected,
        onError
    );
}

// 연결 성공 시 채팅방 목록 로드
function onConnected() {
    loadChatRooms();
}

function onError(error) {
    console.error('STOMP 연결 실패:', error);
    // 필요 시 재연결 로직 구현 가능
}

// 채팅방 목록 조회
function loadChatRooms() {
    httpRequest(
        'GET',
        '/api/admin/chat/rooms',
        null,
        async (res) => {
            const rooms = await res.json();
            renderChatRoomList(rooms);
            if (rooms.length > 0 && !selectedRoomId) {
                selectRoom(rooms[0].roomId);
            }
        },
        (err) => console.error('채팅방 목록 로드 실패:', err)
    );
}

// 채팅방 목록 UI 렌더링
function renderChatRoomList(rooms) {
    const listEl = document.getElementById('room-list');
    listEl.innerHTML = '';
    rooms.forEach(room => {
        const li = document.createElement('li');
        li.textContent = `방 번호: ${room.roomId}`;
        li.style.cursor = 'pointer';
        li.onclick = () => selectRoom(room.roomId);
        listEl.appendChild(li);
    });
}

// 특정 채팅방 선택
function selectRoom(roomId) {
    if (selectedRoomId === roomId) return;

    unsubscribeRoom();
    selectedRoomId = roomId;
    updateChatRoomUI(roomId);
    subscribeRoom(roomId);
    loadChatHistory(roomId);
}

// 이전 구독 해제
function unsubscribeRoom() {
    if (currentSubscription) {
        currentSubscription.unsubscribe();
        currentSubscription = null;
    }
}

// 채팅방 UI 초기화
function updateChatRoomUI(roomId) {
    document.getElementById('current-room-name').textContent = `채팅방 ${roomId}`;
    document.getElementById('chat-messages').innerHTML = '';
}

// 채팅방 구독
function subscribeRoom(roomId) {
    currentSubscription = stompClient.subscribe(`/sub/chat/room/${roomId}`, (messageOutput) => {
        const message = JSON.parse(messageOutput.body);
        const senderType = message.sender === 'admin' ? 'admin' : 'user';
        addMessageToChat(senderType, message.content);
    });
}

// 채팅 기록 불러오기
function loadChatHistory(roomId) {
    httpRequest(
        'GET',
        `/api/chat/messages?roomId=${roomId}`,
        null,
        async (res) => {
            const messages = await res.json();
            messages.forEach(msg => {
                const senderType = msg.sender === 'admin' ? 'admin' : 'user';
                addMessageToChat(senderType, msg.content);
            });
        },
        (err) => console.error('채팅 기록 불러오기 실패:', err)
    );
}

// 채팅 메시지 출력
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
    const input = document.getElementById('chat-input');
    const message = input.value.trim();

    if (!message || !selectedRoomId || !stompClient) return;

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

// 페이지 로딩 시 WebSocket 연결 시작
window.onload = () => {
    connectAdmin();
};
