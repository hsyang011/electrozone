// 실시간 채팅 기능
// 서버에서 로그인 사용자명을 JS 변수로 주입했다고 가정
const currentUser = "user";
const chatPartner = "admin";  // 상대가 관리자일 때

let stompClient = null;
let roomId;

// 채팅 팝업 토글
function toggleChatPopup() {
    const chatPopup = document.getElementById('chat-popup');
    chatPopup.classList.toggle('hidden');
    if (!stompClient) connect();
}

// STOMP 연결 및 메시지 구독
function connect() {
    const socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({
        Authorization: "Bearer " + localStorage.getItem("access_token")
    }, function(frame) {
        fetch('/api/chat/room', {
            method : 'POST',
            headers : {
                // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
                Authorization : 'Bearer ' + localStorage.getItem('access_token'),
                'Content-Type' : 'application/json',
            }
        }).then(response => response.json())
        .then(data => {
            console.log('방번호:', data.roomId);
            roomId = data.roomId;
            loadChatHistory();
            console.log('Connected: ' + frame);
            stompClient.subscribe('/sub/chat/room/' + roomId, function(messageOutput) {
                const message = JSON.parse(messageOutput.body);
                // 보낸 사람이 현재 사용자면 'user', 아니면 'admin' 스타일로 표시
                console.log('받은 메시지:', message);
                const senderType = message.sender === currentUser ? 'user' : 'admin';
                addMessageToChat(senderType, message.content);
            });
        }).catch(err => console.error('에러:', err));
    }, function(error) {
        console.error('STOMP 연결 에러:', error);
        // 필요시 재연결 로직 추가 가능
    });
}

// 메시지 화면에 추가
function addMessageToChat(sender, message) {
    const chatBox = document.getElementById('chat-messages');
    const msgDiv = document.createElement('div');
    msgDiv.className = `message ${sender}`;
    msgDiv.innerText = message;
    chatBox.appendChild(msgDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

// 메시지 전송 함수
function sendMessage(event) {
    event.preventDefault();
    const input = document.getElementById('chat-input');
    const message = input.value.trim();
    if (message === '' || !stompClient) return;

    const chatMessage = {
        roomId: roomId,
        sender: currentUser,
        receiver: chatPartner,
        content: message,
        type: 'CHAT'
    };

    stompClient.send('/app/chat/message', { Authorization: "Bearer " + localStorage.getItem("access_token") }, JSON.stringify(chatMessage));
    input.value = '';
}

// 이전 채팅 기록 불러오기
function loadChatHistory() {
    fetch(`/api/chat/messages?roomId=${roomId}`, {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type' : 'application/json'
        }
    })
    .then(response => response.json())
    .then(messages => {
        messages.forEach(message => {
            const senderType = message.sender === currentUser ? 'user' : 'admin';
            addMessageToChat(senderType, message.content);
        });
    })
    .catch(err => console.error('이전 채팅 기록 불러오기 실패:', err));
}
