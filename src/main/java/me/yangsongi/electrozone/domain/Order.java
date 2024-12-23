package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;  // 주문 날짜입니다.

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;  // 총 주문 금액입니다.

    @Column(name = "status", nullable = false)
    private String status;  // 주문 상태입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 주문 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 주문 수정 시간입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 사용자와의 N:1 단방향 관계입니다.

}
