package com.ssafy.rit.back.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Table(name = "group_recommend_book")
public class GroupRecommendBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_recommend_book_id")
    private int id;

    @Column(name = "cover")
    private String cover;

    @Column(name = "re_group")
    private int reGroup;

    @Column(name = "created_at")
    private LocalDate createdAt;

//    관계 설정
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book bookId;
}
