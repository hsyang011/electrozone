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

// 리뷰 등록 기능
function addReview(event) {
    event.preventDefault();
    const form = event.target;
    const body = JSON.stringify({
        productId: form.productId.value,
        content: form.content.value
    });
    const success = () => {
        alert('등록 완료되었습니다.');
    };
    const fail = () => {
        alert('등록 실패했습니다.');
    }

    httpRequest(form.method, form.action, body, success, fail);
}
