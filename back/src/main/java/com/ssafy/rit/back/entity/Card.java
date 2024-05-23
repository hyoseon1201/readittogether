package com.ssafy.rit.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "card")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "create_at")
    private LocalDate createdAt;

    @Column(name = "deleted_by_sender", columnDefinition = "BOOLEAN DEFAULT FALSE") //기본값은 FALSE
    private boolean deletedBySender = false;

    @Column(name = "deleted_by_recipient", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deletedByRecipient = false;

//    관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private Member fromMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private Member toMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book bookId;

    @OneToMany(mappedBy = "cardId")
    private List<Postbox> postboxes;



}
