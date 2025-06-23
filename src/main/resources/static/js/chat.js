// 실시간 채팅 기능
const currentUser = "user";
const chatPartner = "admin";
let stompClient = null;
let roomId = null;

// 채팅 팝업 토글
function toggleChatPopup() {
    const popup = document.getElementById('chat-popup');
    popup.classList.toggle('hidden');
    if (!stompClient) connect();
}

// STOMP 연결
function connect() {
    const socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({
        Authorization: "Bearer " + localStorage.getItem("access_token")
    }, onConnected, onError);
}

function onConnected(frame) {
    console.log('STOMP 연결됨:', frame);

    // 채팅방 생성 or 조회
    httpRequest('POST', '/api/chat/room', null, async (res) => {
        const data = await res.json();
        roomId = data.roomId;
        loadChatHistory();
        subscribeToRoom(roomId);
    }, console.error);
}

function onError(error) {
    console.error('STOMP 연결 실패:', error);
}

// 구독 설정
function subscribeToRoom(roomId) {
    stompClient.subscribe(`/sub/chat/room/${roomId}`, (messageOutput) => {
        const message = JSON.parse(messageOutput.body);
        const senderType = message.sender === currentUser ? 'user' : 'admin';
        addMessageToChat(senderType, message.content);
    });
}

// 채팅 메시지 전송
function sendMessage(event) {
    event.preventDefault();
    const input = document.getElementById('chat-input');
    const content = input.value.trim();
    if (!content || !stompClient) return;

    const message = {
        roomId,
        sender: currentUser,
        receiver: chatPartner,
        content,
        type: 'CHAT'
    };

    stompClient.send('/app/chat/message', {
        Authorization: "Bearer " + localStorage.getItem("access_token")
    }, JSON.stringify(message));

    input.value = '';
}

// 채팅 메시지를 화면에 출력
function addMessageToChat(sender, message) {
    const chatBox = document.getElementById('chat-messages');
    const msgDiv = document.createElement('div');
    msgDiv.className = `message ${sender}`;
    msgDiv.innerText = message;
    chatBox.appendChild(msgDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

// 이전 채팅 불러오기
function loadChatHistory() {
    const url = `/api/chat/messages?roomId=${roomId}`;
    httpRequest('GET', url, null, async (res) => {
        const messages = await res.json();
        messages.forEach(msg => {
            const senderType = msg.sender === currentUser ? 'user' : 'admin';
            addMessageToChat(senderType, msg.content);
        });
    }, console.error);
}
