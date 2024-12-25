window.onload = () => {
    const success = (response) => {
        let userHtmlContent = '';
        let orderHtmlContent = '';
        response.json().then(mypage => {
            console.log(mypage);
            userHtmlContent = `
                <h3>회원 정보</h3>
                <p><strong>닉네임 :</strong> <span>${mypage.user.nickname}</span></p>
                <p><strong>이메일 :</strong> <span>${mypage.user.email}</span></p>
                <p><strong>가입일 :</strong> <span>${mypage.user.createdAt}</span></p>
           `;
            mypage.orders.forEach(order => {
                orderHtmlContent += `
                    <div class="order-item">
                        <img src="${order.orderItems[0].imageUrl}" alt="상품 이미지" class="product-image">
                        <div class="order-details">
                            <p><strong>상품명 :</strong> <span>${order.orderItems[0].name} 외 ${order.orderItems.length-1}건</span></p>
                            <p><strong>가격 :</strong> <span>&#8361;${order.totalAmount.toLocaleString()}</span>원</p>
                            <p><strong>주문일 :</strong> <span>${order.orderDate}</span></p>
                            <p><strong>상태 :</strong> <span>${order.status}</span></p>
                        </div>
                        <!-- 취소 및 반품 버튼 -->
                        <div class="order-actions">
                            <button class="detail-btn">상세보기</button>
                            <button class="cancel-btn">취소/반품</button>
                        </div>
                    </div>
                `;
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
