package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.dto.AddToCartRequest;
import me.yangsongi.electrozone.dto.AddToCartResponse;
import me.yangsongi.electrozone.dto.CartViewResponse;
import me.yangsongi.electrozone.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request, Principal principal) {
        CartItem cartItem = cartService.addToCart(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AddToCartResponse(
                cartItem.getProduct().getName(),
                cartItem.getQuantity()));
    }

    @GetMapping("/api/cart")
    public ResponseEntity<List<CartViewResponse>> getCartItems(Principal principal) {
        List<CartViewResponse> cartItems = cartService.getCartItems(principal.getName()).stream()
                .map(CartViewResponse::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }

    @DeleteMapping("/api/cart/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartItem(cartItemId);

        return ResponseEntity.ok().build();
    }

}
