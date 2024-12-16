function login(event) {
    event.preventDefault();
    const form = event.target;

    fetch(form.action, {
        method: 'POST',
        body: new FormData(form), // 폼 데이터를 보내는 방식
    }).then(response => {
        if (response.ok) {
            alert('로그인에 성공하였습니다!');
            location.href = '/';
        } else {
            alert('로그인에 실패하였습니다.');
        }
    }).catch(error => {
       alert(error.message);
    });
}
