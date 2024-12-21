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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;  // 리뷰 고유 ID입니다.

    @Column(name = "rating", nullable = false)
    private int rating;  // 상품에 대한 평점입니다.

    @Column(name = "comment")
    private String comment;  // 상품에 대한 댓글입니다.

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 리뷰 생성 시간입니다.

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 리뷰 수정 시간입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;  // 사용자와의 N:1 단방향 관계입니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;  // 상품과의 N:1 단방향 관계입니다.

}
