package me.yangsongi.electrozone.domain;

public enum OrderStatus {

    ORDERED,    // 주문 완료
    SHIPPING,   // 배송 중
    DELIVERED,  // 배송 완료
    CANCELLED,  // 취소 완료
    RETURNED,   // 반품 완료
    RETURN_REQUESTED    // 반품 요청
}

