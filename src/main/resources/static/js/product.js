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
    const success = response => {
        response.json().then(data => {
            alert(data.name + ' ' + data.quantity + '개가 장바구니에 추가되었습니다.');
        })
    }
    const fail = error => {
        alert('장바구니에 추가 도중 오류가 발생하였습니다.');
        console.log(error);
    }

    httpRequest('POST', '/api/cart', body, success, fail);
}
