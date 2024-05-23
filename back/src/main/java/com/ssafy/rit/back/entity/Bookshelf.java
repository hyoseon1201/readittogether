package com.ssafy.rit.back.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "bookshelf")
public class Bookshelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookshelf_id")
    private int id;

    @Column(name = "is_read")
    private Integer isRead;

    @Column(name = "is_rate")
    private Integer isRate;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "cover")
    private String cover;

    @Column(name = "title")
    private String title;

//  관계 설정
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book bookId;

}