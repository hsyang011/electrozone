// 파라미터로 받은 토큰이 있다면 로컬스토리지에 저장
const token = new URLSearchParams(location.search).get('token');
if (token) {
    localStorage.setItem('access_token', token);
}
