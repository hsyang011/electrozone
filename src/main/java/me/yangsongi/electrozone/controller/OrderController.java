package me.yangsongi.electrozone.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.CartItem;
import me.yangsongi.electrozone.domain.Order;
import me.yangsongi.electrozone.dto.OrderProcessRequest;
import me.yangsongi.electrozone.dto.OrderProcessResponse;
import me.yangsongi.electrozone.dto.OrderStatusResponse;
import me.yangsongi.electrozone.service.CartService;
import me.yangsongi.electrozone.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final IamportClient iamportClient = new IamportClient("2362258855365655", "x1i5g1nJtm2izm79QkGrsJpob555fBy86TfGKVLCJeIM2ye5z62YhlcJnx3pgGPJu8mPefc7AxhDJww2");

    @GetMapping("/checkout")
    public String order() {
        return "checkout";
    }

    @PostMapping("/api/checkout/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid")String imp_uid, @RequestBody OrderProcessRequest request, Principal principal) throws IamportResponseException, IOException {

        // 사용자 정보를 기반으로 주문 생성
        List<CartItem> cartItems = cartService.getCartItems(principal.getName());

        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);

        if (payment != null) {
            // 결제 성공 시 주문 완료 처리
            orderService.completeOrder(principal.getName(), request, cartItems);

            return payment;
        } else {
            return null;
        }
    }
//    public ResponseEntity<Order> orderProcess(@RequestBody OrderProcessRequest request, Principal principal) {
//        try {
//            // 사용자 정보를 기반으로 주문 생성
//            List<CartItem> cartItems = cartService.getCartItems(principal.getName());
//            int totalPrice = request.payment();
//
//            // 결제 서비스에서 실제 결제 처리 (예시로 카드 결제)
//            boolean paymentSuccess = orderService.payment(request.paymentMethod(), totalPrice);
//
//            if (paymentSuccess) {
//                // 결제 성공 시 주문 완료 처리
//                Order savedOrder = orderService.completeOrder(principal.getName(), request, cartItems);
//                return ResponseEntity.ok().body(savedOrder);
//            } else {
//                return ResponseEntity.unprocessableEntity().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    // 주문 취소
    @PostMapping("/api/order/{orderId}/cancel")
    @ResponseBody
    public ResponseEntity<OrderStatusResponse> cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        Order order = orderService.cancelOrder(orderId);

        return ResponseEntity.ok().body(new OrderStatusResponse(order));
    }

    // 반품 처리
    @PostMapping("/api/order/{orderId}/return")
    @ResponseBody
    public ResponseEntity<OrderStatusResponse> returnOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        Order order = orderService.requestReturnOrder(orderId);

        return ResponseEntity.ok().body(new OrderStatusResponse(order));
    }

}
