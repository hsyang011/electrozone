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
        alert('네트워크 오류: ' + error.message);  // 네트워크 오류에 대한 메시지 처리
    });
}
