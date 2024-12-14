package me.yangsongi.electrozone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;  // 카테고리 고유 ID입니다.

    @Column(name = "name", nullable = false)
    private String name;  // 카테고리 이름입니다.

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parentcategory_id")
//    private Category parentCategory;  // 부모 카테고리와의 N:1 단방향 관계입니다.

}
