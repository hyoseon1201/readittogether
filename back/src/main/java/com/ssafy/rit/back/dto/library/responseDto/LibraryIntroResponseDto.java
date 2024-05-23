package com.ssafy.rit.back.dto.library.responseDto;

import com.ssafy.rit.back.entity.Follow;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LibraryIntroResponseDto {

    private String intro;

    private int isMine;

    private int isReceive;

    private String nickname;

    private String profileImage;

    private int followingNum;

    private int followerNum;

    private int isFollowing;

    private String email;

    private List<FollowingDto> followingList;

    private List<FollowerDto> followerList;

}
