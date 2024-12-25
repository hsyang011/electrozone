package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;  // 주문 고유 ID입니다.

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;  // 주문 날짜입니다.

    @Column(name = "payment", nullable = false)
    private Integer payment;  // 총 주문 금액입니다.

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;  // 주문 상태입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 주문 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 주문 수정 시간입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 사용자와의 N:1 단방향 관계입니다.

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
