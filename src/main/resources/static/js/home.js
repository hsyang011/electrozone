function getProductsByCategory(category) {
    fetch('/api/home?category=' + category, {
        method: 'GET'
    }).then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('카테고리로 상품을 가져오는 도중 오류 발생');
    }).then(products => {
        let htmlContent = '';
        products.forEach(product => {
            htmlContent += `
            <div class="product-item" onclick="location.href='/products/${product.productId}'">
                <img src="${product.imageUrl}" alt="상품">
                <p>${product.name}</p>
                <span>&#8361;${product.price.toLocaleString()}</span>
            </div>
        `;
        });
        document.getElementById('product-list').innerHTML = htmlContent;
    }).catch(error => {
        console.log(error);
    })
}
