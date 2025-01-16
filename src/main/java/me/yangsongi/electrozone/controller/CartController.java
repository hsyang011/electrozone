package me.yangsongi.electrozone.controller;

import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.dto.AddToCartRequest;
import me.yangsongi.electrozone.dto.AddToCartResponse;
import me.yangsongi.electrozone.dto.CartViewResponse;
import me.yangsongi.electrozone.dto.UpdateCartItemRequest;
import me.yangsongi.electrozone.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @PostMapping("/api/cart")
    @ResponseBody
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request, Principal principal) {
        CartItem cartItem = cartService.addToCart(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AddToCartResponse(
                cartItem.getProduct().getName(),
                request.quantity()));
    }

    @GetMapping("/api/cart")
    @ResponseBody
    public ResponseEntity<List<CartViewResponse>> getCartItems(Principal principal) {
        List<CartViewResponse> cartItems = cartService.getCartItems(principal.getName()).stream()
                .map(CartViewResponse::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }

    @PutMapping("/api/cart/{cartItemId}")
    @ResponseBody
    public ResponseEntity<Void> updateCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestBody UpdateCartItemRequest request) {
        cartService.updateCartItem(cartItemId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/cart/{cartItemId}")
    @ResponseBody
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartItem(cartItemId);

        return ResponseEntity.ok().build();
    }

}
