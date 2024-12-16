function login(event) {
    event.preventDefault();
    const form = event.target;

    fetch(form.action, {
        method: 'POST',
        body: new FormData(form), // 폼 데이터를 보내는 방식
    }).then(response => {
        if (response.status === 200) { // 로그인 성공 상태 코드 확인
            alert('로그인에 성공하였습니다!');
            location.href = '/';
        } else if (response.status === 401) {
            alert('로그인에 실패하였습니다. 아이디나 비밀번호를 확인해주세요.');
        } else {
            alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    }).catch(error => {
        alert('로그인 오류 발생: ' + error.message);  // 네트워크 오류에 대한 메시지 처리
    });
}

function signup(event) {
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
        if (response.status === 201) {  // 회원가입 성공 상태 코드 (201 Created)
            alert('회원가입에 성공하였습니다!');
            location.href = '/login';  // 로그인 페이지로 리디렉션
        } else if (response.status === 400) {
            alert('입력한 정보에 오류가 있습니다. 다시 확인해주세요.');
        } else {
            alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    }).catch(error => {
        alert('회원가입 오류 발생: ' + error.message);
    });
}

function logout() {
    fetch('/logout', {
        method: 'GET', // 로그아웃은 GET 방식으로 보내기
    }).then(response => {
        if (response.status === 200) {
            alert('로그아웃 되었습니다.');
            location.href = '/';  // 로그아웃 후 홈으로 리디렉션
        } else {
            alert('로그아웃 중 오류가 발생했습니다.');
        }
    }).catch(error => {
        alert('로그아웃 중 오류 발생: ' + error.message);
    });
}
