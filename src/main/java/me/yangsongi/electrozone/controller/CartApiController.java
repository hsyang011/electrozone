package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.dto.AddToCartRequest;
import me.yangsongi.electrozone.dto.AddToCartResponse;
import me.yangsongi.electrozone.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request, Principal principal) {
        CartItem cartItem = cartService.addToCart(request, principal);

        return ResponseEntity.ok().body(new AddToCartResponse(
                cartItem.getProduct().getName(),
                cartItem.getQuantity()));
    }

}
