let orders = [];

window.onload = () => {
    const success = (response) => {
        let userHtmlContent = '';
        let orderHtmlContent = '';
        response.json().then(mypage => {
            userHtmlContent = `
                <h3>회원 정보</h3>
                <p><strong>닉네임 :</strong> <span>${mypage.user.nickname}</span></p>
                <p><strong>이메일 :</strong> <span>${mypage.user.email}</span></p>
                <p><strong>가입일 :</strong> <span>${mypage.user.createdAt}</span></p>
           `;
            orders = mypage.orders;
            let index = 0;
            mypage.orders.forEach(order => {
                orderHtmlContent += `
                    <div class="order-item">
                        <img src="${order.orderItems[0].imageUrl}" alt="상품 이미지" class="product-image">
                        <div class="order-details" id="order-details${index}">
                            <p><strong>상품명 :</strong> <span>${order.orderItems[0].name} 외 ${order.orderItems.length-1}건</span></p>
                            <p><strong>가격 :</strong> <span>&#8361;${order.payment.toLocaleString()}</span>원</p>
                            <p><strong>주문일 :</strong> <span>${order.orderDate}</span></p>
                            <p><strong>상태 :</strong> <span>${order.orderStatus}</span></p>
                        </div>
                        <!-- 취소 및 반품 버튼 -->
                        <div class="order-actions">
                            <button class="detail-btn" onclick="orderDetails(${index})">상세보기</button>
                            <button class="cancel-btn" onclick="cancelOrReturnOrder(${index})"">취소/반품</button>
                        </div>
                    </div>
                `;
                index++;
            }) ;
            document.getElementById('user-info').innerHTML = userHtmlContent;
            document.getElementById('order-list').innerHTML = orderHtmlContent;
        });
    };
    const fail = () => {
        alert('주문내역이 없습니다.');
    }

    httpRequest('GET', '/api/mypage', null, success, fail);
}

function orderDetails(index) {
    let htmlContent = '';
    orders[index].orderItems.forEach(orderItem => {
        htmlContent += `
            <p><strong>상품명 :</strong> <span>${orderItem.name}</span></p>
            <p><strong>가격 :</strong> <span>&#8361;${(orderItem.price * orderItem.quantity).toLocaleString()}</span>원</p>
            <p><strong>주문일 :</strong> <span>${orders[index].orderDate}</span></p>
            <p><strong>상태 :</strong> <span>${orders[index].orderStatus}</span></p>
        `;

        document.getElementById('order-details' + index).innerHTML = htmlContent;
    });
}

function cancelOrReturnOrder(index) {
    const orderId = orders[index].orderId;
    const orderStatus = orders[index].orderStatus;

    switch (orderStatus) {
        case "ORDERED":
            cancelOrder(orderId);
            break;
        case "DELIVERED":
            returnOrder(orderId);
            break;
        default:
            alert('현재 상태에서는 취소/반품이 불가능합니다.');
    }
}

// 주문 취소 요청
function cancelOrder(orderId) {
    const success = (response) => {
        response.json().then((order) => {
            alert('주문이 취소되었습니다.');
            location.reload(); // 페이지 새로고침
        });
    };

    const fail = () => {
        alert('주문 취소에 실패했습니다.');
    };

    httpRequest('POST', `/api/order/${orderId}/cancel`, null, success, fail);
}

// 상품 반품 요청
function returnOrder(orderId) {
    const success = (response) => {
        response.json().then((order) => {
            alert('반품 요청이 완료되었습니다.');
            location.reload(); // 페이지 새로고침
        });
    };

    const fail = () => {
        alert('반품 요청에 실패했습니다.');
    };

    httpRequest('POST', `/api/order/${orderId}/return`, null, success, fail);
}
