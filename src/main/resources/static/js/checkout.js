window.onload = () => {
    const success = (response) => {
        let htmlContent = '';
        response.json().then(orderItems => {
            orderItems.forEach(orderItem => {
                htmlContent += `
                    <div class="order-item">
                        <img src="${orderItem.imageUrl}" alt="상품 이미지">
                        <div class="order-details">
                            <p>상품명 : ${orderItem.name}</p>
                            <p>수량 : ${orderItem.quantity}</p>
                            <p>가격 : &#8361;${orderItem.price.toLocaleString()}원</p>
                        </div>
                    </div>
                `;
            });
            document.getElementById('order-item').innerHTML = htmlContent;
        });
    };

    const fail = () => {
        alert('주문 정보를 불러오는 데 실패했습니다.');
    };

    // 장바구니 데이터를 API에서 받아오기
    httpRequest('GET', '/api/cart', null, success, fail);
};

function checkout() {
    const form = document.checkoutForm;

    const body = JSON.stringify({
        recipient: form.recipient.value,
        address: form.address.value,
        phone: form.phone.value,
        paymentMethod: document.getElementById('payment-method').value
    });

    const success = (response) => {
        alert('주문이 완료되었습니다!');
        location.href = '/mypage';  // 주문 완료 후 확인 페이지로 이동
    }
    const fail = () => {
        alert('주문 처리 중 문제가 발생했습니다.');
    }

    httpRequest(form.method, form.action, body, success, fail);
}
