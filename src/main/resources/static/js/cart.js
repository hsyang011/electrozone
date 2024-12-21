function updateTotalPrice(price) {
    console.log(price);
    const quantity = document.getElementById('quantity').value;  // 선택된 수량

    // 총 금액 계산
    const totalPrice = parseInt(price) * parseInt(quantity);

    // 총 금액을 화면에 업데이트
    document.getElementById('totalPrice').innerHTML = '총 금액 : &#8361;' + totalPrice.toLocaleString();
}
