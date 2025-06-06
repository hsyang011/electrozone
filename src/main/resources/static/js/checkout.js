let payment = 0;

window.onload = () => {
    const success = (response) => {
        let orderItemHtmlContent = '';
        let paymentHtmlContent = '';
        response.json().then(orderItems => {
            orderItems.forEach(orderItem => {
                payment += orderItem.price * orderItem.quantity;
                orderItemHtmlContent += `
                    <div class="order-item">
                        <img src="${orderItem.imageUrl}" alt="상품 이미지">
                        <div class="order-details">
                            <p class="order-item-name">상품명 : ${orderItem.name}</p>
                            <p>수량 : ${orderItem.quantity}</p>
                            <p>가격 : &#8361;${orderItem.price.toLocaleString()}원</p>
                        </div>
                    </div>
                `;
            });
            paymentHtmlContent = `
                <p><strong>상품금액:</strong> ${payment.toLocaleString()}원</p>
                <p><strong>배송비:</strong> 3,000원</p>
                <p><strong>총 결제 금액:</strong> ${(payment + 3000).toLocaleString()}원</p>
            `
            document.getElementById('order-item').innerHTML = orderItemHtmlContent;
            document.getElementById('payment').innerHTML = paymentHtmlContent;
        });
    };

    const fail = () => {
        alert('주문 정보를 불러오는 데 실패했습니다.');
    };

    // 장바구니 데이터를 API에서 받아오기
    httpRequest('GET', '/api/cart', null, success, fail);
};

function checkout() {
//    const form = document.checkoutForm;
//
//    const body = JSON.stringify({
//        recipient: form.recipient.value,
//        address: form.address.value,
//        phone: form.phone.value,
//        payment: payment+3000,
//        paymentMethod: document.getElementById('payment-method').value
//    });
//
//    const success = (response) => {
//        alert('주문이 완료되었습니다!');
//        location.href = '/mypage';  // 주문 완료 후 확인 페이지로 이동
//    }
//    const fail = () => {
//        alert('주문 처리 중 문제가 발생했습니다.');
//    }
//
//    httpRequest(form.method, form.action, body, success, fail);
    let IMP = window.IMP;
    IMP.init("imp25411281");
    let pm = document.getElementById('payment-method').value;
    let pg = '';
    if (pm == '카카오페이') {
        pg = 'kakaopay'
    } else if (pm == '토스페이') {
        pg = 'tosspay'
    } else if (pm == '신용카드') {
        pg = 'html5_inicis.INIBillTst'
    }
    console.log(pm + " : " + pg);
    const form = document.checkoutForm;

    IMP.request_pay({
        pg: pg,
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getTime(),   // 주문번호
        name: document.getElementsByClassName('order-item-name')[0].value + ' 외',
        amount: payment+3000,                         // 숫자 타입
        buyer_email: form.recipient.value,
        buyer_name: form.recipient.value,
        buyer_tel: form.phone.value,
        buyer_addr: form.address.value,
        buyer_postcode: "00000"
    }, function (rsp) { // callback
        const body = JSON.stringify({
            recipient: form.recipient.value,
            address: form.address.value,
            phone: form.phone.value,
            payment: payment+3000,
            paymentMethod: document.getElementById('payment-method').value
        });

        const success = () => {
            alert('결제 성공하였습니다.');
            location.href = '/mypage';
        }

        const fail = (data) => {
            console.log(rsp);
            alert('결제 실패하였습니다.')
        }

        httpRequest(form.method, form.action + '/' + rsp.imp_uid, body, success, fail);
    });
}
