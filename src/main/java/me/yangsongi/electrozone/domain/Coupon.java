package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;  // 쿠폰 고유 ID입니다.

    @Column(name = "code")
    private String code;  // 쿠폰 코드입니다.

    @Column(name = "discount")
    private Double discount;  // 할인율입니다.

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;  // 쿠폰 만료 날짜입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 쿠폰 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 쿠폰 수정 시간입니다.

}
