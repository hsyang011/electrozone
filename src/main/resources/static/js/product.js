// 수량 변경하는 함수
function updateTotalPrice(price) {
    const quantity = document.getElementById('quantity').value;  // 선택된 수량

    // 총 금액 계산
    const totalPrice = parseInt(price) * parseInt(quantity);

    // 총 금액을 화면에 업데이트
    document.getElementById('totalPrice').innerHTML = '총 금액 : &#8361;' + totalPrice.toLocaleString();
}

// 장바구니에 추가 요청을 보내는 함수
function addToCart(productId) {
    let body = JSON.stringify({
        productId: productId,
        quantity: document.getElementById('quantity').value
    });
    function success(response) {
        response.json().then(data => {
            alert(data.name + ' ' + data.quantity + '개가 장바구니에 추가되었습니다.');
        })
    }
    function fail(error) {
        alert('장바구니에 추가 도중 오류가 발생하였습니다.');
        console.log(error);
    }

    httpRequest('POST', '/api/cart', body, success, fail);
}

// 쿠키를 가져오는 함수
function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(';');
    cookie.some(item => {
        item = item.replace(' ', '');

        let dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method : method,
        headers : {
            // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization : 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type' : 'application/json',
        },
        body : body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success(response);
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method : 'POST',
                headers : {
                    Authorization : 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type' : 'application/json',
                },
                body : JSON.stringify({
                    refresh_token : getCookie('refresh_token'),
                }),
            }).then(response => {
                if (response.ok) {
                    return response.json();
                }
            }).then(result => {
                // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                localStorage.setItem('access_token', result.accessToken);
                httpRequest(method, url, body, success, fail);
            }).catch(error => fail(error));
        } else {
            return fail();
        }
    });
}
