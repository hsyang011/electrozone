package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;  // 주문 상세 고유 ID입니다.

    @Column(name = "quantity")
    private int quantity;  // 주문 상품 수량입니다.

    @Column(name = "price")
    private BigDecimal price;  // 주문 상품 가격입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 주문 상세 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 주문 상세 수정 시간입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;  // 주문과의 N:1 단방향 관계입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;  // 상품과의 N:1 단방향 관계입니다.

}
