package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;  // 장바구니 항목 고유 ID입니다.

    @Column(name = "quantity", nullable = false)
    private Integer quantity;  // 상품 수량입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id")
    private Cart cart;  // 장바구니와의 N:1 단방향 관계입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;  // 상품과의 N:1 단방향 관계입니다.

    // Cart 설정 메서드
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
