package com.ssafy.rit.back.dto.member.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowMemberResponseDto {

    private Long memberId;
    private String profileImage;
    private String nickname;
    private int isFollowing;
}
