// 파라미터로 받은 토큰이 있다면 로컬스토리지에 저장
const oauth2token = new URLSearchParams(location.search).get('token');
if (oauth2token) {
    localStorage.setItem('access_token', oauth2token);
    alert('로그인에 성공하였습니다!');
    location.href = '/';
}

function login(event) {
    event.preventDefault();
    const form = event.target;

    fetch(form.action, {
        method: form.method,
        body: new FormData(form), // 폼 데이터를 보내는 방식
        credentials: 'same-origin'  // Same-origin policy를 따르기
    }).then(response => {
        if (response.status === 200) { // 200-299 상태 코드 범위
            const token = response.headers.get('Authorization');
            if (token) {
                localStorage.setItem('access_token', token);
                alert('로그인에 성공하였습니다!');
                location.href = '/';
            } else {
                alert('액세스 토큰을 받지 못했습니다.');
            }
        } else if (response.status === 401) {
            alert('로그인에 실패하였습니다. 아이디나 비밀번호를 확인해주세요.');
        } else {
            alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    }).catch(error => {
        alert('로그인 중 오류 발생: ' + error.message);
    });
}

function join(event) {
    event.preventDefault();
    const form = event.target;

    // 비밀번호 확인 로직 추가
    const password = form.password.value;
    const confirmPassword = form.confirmPassword.value;

    if (password !== confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }

    fetch(form.action, {
        method: 'POST',
        body: new FormData(form),  // 폼 데이터를 보내는 방식
    }).then(response => {
        if (response.status === 201) {  // 201 Created 포함된 상태 코드 확인
            alert('회원가입에 성공하였습니다!');
            location.href = '/login';  // 로그인 페이지로 리디렉션
        } else if (response.status === 400) {
            // 서버에서 반환된 오류 메시지 처리
            response.json().then(errors => {
                if (errors) {
                    // 서버에서 받은 errors 배열을 출력
                    alert(errors.join('\n'));
                } else {
                    alert('입력한 정보에 오류가 있습니다. 다시 확인해주세요.');
                }
            });
        } else {
            alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    }).catch(error => {
        alert('회원가입 중 오류 발생: ' + error.message);
    });
}

function logout() {
    const success = () => {
        fetch('/logout', {
            method: 'GET', // 로그아웃은 POST 방식으로 보내기
        }).then(response => {
            if (response.ok) {
                // 로컬 스토리지에 저장된 액세스 토큰을 삭제
                localStorage.removeItem('access_token');
                document.cookie = 'refresh_token=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
                alert('로그아웃 되었습니다.');
                location.href = '/';  // 로그아웃 후 홈으로 리디렉션
            } else {
                alert('로그아웃 중 오류가 발생했습니다.');
            }
        }).catch(error => {
            alert('로그아웃 중 오류 발생: ' + error.message);
        });
    };

    const fail = () => {
        console.warn('리프레시 토큰을 삭제하는 데 실패하였습니다.');
        // 리프레시 토큰 삭제 실패하더라도 로그아웃을 진행
        success();
    }

    httpRequest('DELETE', '/api/refresh-token', null, success, fail);
}

function parseJwt(token) {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}

const token = localStorage.getItem('access_token');
const loginLink = document.getElementById('login-link');
const joinLink = document.getElementById('join-link');
const logoutLink = document.getElementById('logout-link');
const mypageLink = document.getElementById('mypage');
const cartLink = document.getElementById('cart');
const adminLink = document.getElementById('admin-link');

function showGuestUI() {
    loginLink.style.display = 'inline';
    joinLink.style.display = 'inline';
    logoutLink.style.display = 'none';
    mypageLink.style.display = 'none';
    cartLink.style.display = 'none';
    adminLink.style.display = 'none';
}

function showUserUI(payload) {
    loginLink.style.display = 'none';
    joinLink.style.display = 'none';
    logoutLink.style.display = 'inline';
    mypageLink.style.display = 'inline';
    cartLink.style.display = 'inline';

    if (payload.userRole && payload.userRole.includes('ROLE_ADMIN')) {
        adminLink.style.display = 'inline';
    }
}

if (token) {
    const payload = parseJwt(token);
    const now = Math.floor(Date.now() / 1000); // 현재 시간 (초 단위)

    if (!payload || !payload.exp || payload.exp < now) {
        // 토큰 만료된 경우
        localStorage.removeItem('access_token');
        showGuestUI();
    } else {
        showUserUI(payload);
    }
} else {
    showGuestUI();
}
