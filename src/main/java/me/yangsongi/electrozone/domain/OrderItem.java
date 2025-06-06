package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "quantity", nullable = false)
    private Integer quantity;  // 주문 상품 수량입니다.

    @Column(name = "price", nullable = false)
    private Integer price;  // 주문 상품 가격입니다.

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;  // 상품과의 N:1 단방향 관계입니다.

    public OrderItem(CartItem cartItem, Order order) {
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getProduct().getPrice();
        this.order = order;
        this.product = cartItem.getProduct();
    }

}
