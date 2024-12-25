window.onload = () => {
    const success = (response) => {
        let htmlContent = '';
        let totalPrice = 0;
        response.json().then(cartItems => {
           cartItems.forEach(cartItem => {
               totalPrice += cartItem.price * cartItem.quantity;
               htmlContent += `
                   <tr>
                        <td onclick="location.href='/products/${cartItem.productId}'" style="cursor: pointer;">${cartItem.name}</td>
                        <td><img src="${cartItem.imageUrl}" alt="상품 이미지" class="cart-item-image"></td>
                        <td>
                            <button class="quantity-btn" onclick="updateQuantity('${cartItem.cartItemId}', ${cartItem.quantity - 1})">-</button>
                            <span class="quantity">${cartItem.quantity}</span>
                            <button class="quantity-btn" onclick="updateQuantity('${cartItem.cartItemId}', ${cartItem.quantity + 1})">+</button>
                        </td>
                        <td>&#8361;${(cartItem.price * cartItem.quantity).toLocaleString()}원</td>
                        <td><button class="remove-btn" onclick="deleteCartItem('${cartItem.name}', ${cartItem.cartItemId})">삭제</button></td>
                   </tr>
               `;
           }) ;
           document.getElementById('cart-items').innerHTML = htmlContent;
           document.getElementById('total-price').innerHTML = '총 금액: &#8361;' + totalPrice.toLocaleString() + '원';
        });
    };
    const fail = () => {
        alert('장바구니가 비어있습니다.');
    }

    httpRequest('GET', '/api/cart', null, success, fail);
}

// 수량 변경 API 호출
function updateQuantity(cartItemId, newQuantity) {
    if (newQuantity < 1) {
        alert('수량은 1개 이상이어야 합니다.');
        return;
    }
    // 수량 업데이트를 위한 API 호출
    const body = JSON.stringify({
        quantity: newQuantity
    });

    const success = () => {
        window.onload();
    }
    const fail = () => {
        alert('수량 변경에 실패했습니다.');
    }

    httpRequest('PUT', `/api/cart/${cartItemId}`, body, success, fail);
}

function deleteCartItem(name, cartItemId) {
    const success = () => {
        alert(name + '이 제거되었습니다.');
        location.reload();
    }
    const fail = () => {
        alert(name + '이 제거되지 못했습니다.');
    }

    httpRequest('DELETE', '/api/cart/' + cartItemId, null, success, fail);
}
