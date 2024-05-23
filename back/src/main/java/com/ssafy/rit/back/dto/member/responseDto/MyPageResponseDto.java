package com.ssafy.rit.back.dto.member.responseDto;

import com.ssafy.rit.back.entity.Member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponseDto {

    private String profileImage;
    private String nickname;
    private String email;
    private List<FollowMemberResponseDto> followList;
    private List<FollowMemberResponseDto> followerList;
    // 평가완료한 책 개수
    private int evaluatedBookCnt;
    // 관심 등록한 책 개수
    private int likedBookCnt;
    // 작성한 카드 개수
    private int sendCardCnt;
    // 한달 동안 읽은 책의 장르
    private List<Integer> genreNoList;

}
