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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;  // 상품 고유 ID 입니다.

    @Column(name = "name", nullable = false)
    private String name;  // 상품 이름입니다.

    @Column(name = "price", nullable = false)
    private BigDecimal price;  // 상품 가격입니다.

    @Column(name = "stock", nullable = false)
    private int stock;  // 상품 재고 수량입니다.

    @Column(name = "brand")
    private String brand;  // 상품 브랜드입니다.

    @Column(name = "description")
    private String description;  // 상품 설명입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 상품 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 상품 수정 시간입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;  // 카테고리와의 N:1 단방향 관계입니다.

}
