package com.ssafy.rit.back.dto.library.responseDto;

import lombok.Data;

@Data
public class FollowingDto {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private int isFollowing;
}
