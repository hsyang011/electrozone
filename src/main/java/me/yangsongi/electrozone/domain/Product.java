package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.*;
import me.yangsongi.electrozone.dto.AddProductRequest;
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
@Table(name = "products")
public class Product {

    // 외부 json에서 발급받은 prod_id를 반영하기 위해서 GeneratedValue속성을 제거하고,
    // 대신 unique제약조건을 추가합니다.
    @Id
    @Column(name = "product_id", unique = true)
    private Long productId;  // 상품 고유 ID 입니다.

    @Column(name = "name", nullable = false)
    private String name;  // 상품 이름입니다.

    @Column(name = "price")
    private Integer price;  // 상품 가격입니다.

    @Column(name = "specs", length = 1023)
    private String specs;  // 상품 스펙입니다.

    @Column(name = "image_url")
    private String imageUrl; // 이미지 링크입니다.

    @Column(name = "stock")
    private Integer stock;  // 상품 재고 수량입니다.

    @Column(name = "registered_at")
    private LocalDateTime registeredAt; // 상품의 생산일입니다.

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 상품 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 상품 수정 시간입니다.

    public Product update(AddProductRequest request, LocalDateTime registeredAt) {
        this.name = request.name();
        this.price = request.price();
        this.specs = request.specs().toString();
        this.imageUrl = request.imageUrl();
        this.registeredAt = registeredAt;

        return this;
    }

}
