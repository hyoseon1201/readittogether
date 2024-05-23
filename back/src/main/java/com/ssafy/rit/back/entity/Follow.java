package com.ssafy.rit.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private int id;

//    관계 설정
    // 내가 팔로우
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following")
    private Member followingMember;

    // 나를 팔로우
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private Member followerMember;

}
