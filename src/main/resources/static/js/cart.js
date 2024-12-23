window.onload = () => {
    const success = (response) => {
        let htmlContent = '';
        let totalPrice = 0;
        response.json().then(cartItems => {
           cartItems.forEach(cartItem => {
               totalPrice += cartItem.price;
               htmlContent += `
                   <tr>
                        <td>${cartItem.name}</td>
                        <td><img src="${cartItem.imageUrl}" alt="상품 이미지" class="cart-item-image"></td>
                        <td>${cartItem.quantity}</td>
                        <td>&#8361;${cartItem.price.toLocaleString()}원</td>
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
