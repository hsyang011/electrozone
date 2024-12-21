package me.yangsongi.electrozone.service;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Cart;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.domain.User;
import me.yangsongi.electrozone.dto.AddToCartRequest;
import me.yangsongi.electrozone.repository.CartItemRepository;
import me.yangsongi.electrozone.repository.CartRepository;
import me.yangsongi.electrozone.repository.ProductRepository;
import me.yangsongi.electrozone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItem addToCart(AddToCartRequest request, Principal principal) {
        // 요청한 상품 정보 확인
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 사용자 정보 확인
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자의 장바구니 가져오기 (없으면 생성합니다.)
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .user(user)
                        .build()));

        // 장바구니에 이미 존재하는 상품인지 확인
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            // 기존 수량에 추가 요청 수량 더하기
            cartItem.updateQuantity(cartItem.getQuantity() + request.quantity());
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.quantity())
                    .build(); // 기본 수량 0 설정

            cartItemRepository.save(cartItem);
        }

        return cartItem;
    }

}
