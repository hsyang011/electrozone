window.onload = () => {
    const success = (response) => {
        let htmlContent = '';
        response.json().then(orderItems => {
            orderItems.forEach(orderItem => {
                htmlContent += `
                    <tr>
                        <td>${orderItem.name}</td>
                        <td><img src="${orderItem.imageUrl}" alt="상품 이미지" class="order-item-image"></td>
                        <td>${orderItem.quantity}</td>
                        <td>&#8361;${orderItem.price.toLocaleString()}원</td>
                    </tr>
                `;
            });
            document.getElementById('order-items').innerHTML = htmlContent;
        });
    };

    const fail = () => {
        alert('주문 정보를 불러오는 데 실패했습니다.');
    };

    // 장바구니 데이터를 API에서 받아오기
    httpRequest('GET', '/api/cart', null, success, fail);
};

function checkout(event) {
    event.preventDefault();
    const form = event.target;

    const body = JSON.stringify({
        name: form.name.value,
        address: form.address.value,
        paymentMethod: form.paymentMethod.value
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
