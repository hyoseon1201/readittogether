package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.library.requestDto.LibraryIntroUpdateRequestDto;
import com.ssafy.rit.back.dto.library.response.LibraryIntroResponse;
import com.ssafy.rit.back.dto.library.response.LibraryIntroUpdateResponse;
import com.ssafy.rit.back.dto.library.responseDto.FollowerDto;
import com.ssafy.rit.back.dto.library.responseDto.FollowingDto;
import com.ssafy.rit.back.dto.library.responseDto.LibraryIntroResponseDto;
import com.ssafy.rit.back.entity.Follow;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.FollowRepository;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.service.LibraryService;
import com.ssafy.rit.back.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final MemberRepository memberRepository;
    private final CommonUtil commonUtil;
    private final FollowRepository followRepository;

    // 다른 사람 서재 방문 시 받을 intro 데이터
    @Override
    public ResponseEntity<LibraryIntroResponse> readLibraryIntro(Long memberId) {

        Member currentMember = commonUtil.getMember();

        Member thisMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        int isMine = currentMember.equals(thisMember) ? 1 : 0;
        int isFollowing = followRepository.findFollow(currentMember, thisMember).isPresent() ? 1 : 0;

        List<Long> followerIds = followRepository.findFollowingMemberIdsByFollower(currentMember);

        List<Follow> byFollowingMember = followRepository.findByFollowingMember(thisMember);
        List<FollowingDto> followingDtos = byFollowingMember.stream()
                .map(follow -> {
                    Member follower = follow.getFollowerMember();
                    FollowingDto dto = new FollowingDto();
                    int temp = 0;
                    if (followerIds.contains(follower.getId())) {
                        temp = 1;
                    }
                    dto.setMemberId(follower.getId());
                    dto.setNickname(follower.getNickname());
                    dto.setProfileImage(follower.getProfileImage());
                    dto.setIsFollowing(followerIds.contains(follower.getId()) ? 1 : 0);
                    return dto;
                })
                .toList();

        List<Follow> byFollowerMember = followRepository.findByFollowerMember(thisMember);
        List<FollowerDto> followerDtos = byFollowerMember.stream()
                .map(follow -> {
                    Member following = follow.getFollowingMember();
                    FollowerDto dto = new FollowerDto();
                    int temp = 0;
                    if (followerIds.contains(following.getId())) {
                        temp = 1;
                    }
                    dto.setMemberId(following.getId());
                    dto.setNickname(following.getNickname());
                    dto.setProfileImage(following.getProfileImage());
                    dto.setIsFollowing(followerIds.contains(following.getId()) ? 1 : 0);
                    return dto;
                })
                .toList();

        LibraryIntroResponseDto detailDto = LibraryIntroResponseDto.builder()
                .isMine(isMine)
                .isReceive(thisMember.getIsReceivable())
                .nickname(thisMember.getNickname())
                .profileImage(thisMember.getProfileImage())
                .isFollowing(isFollowing)
                .intro(thisMember.getIntro())
                .email(thisMember.getEmail())
                .followerNum(followRepository.countByFollowerMember(thisMember))
                .followingNum(followRepository.countByFollowingMember(thisMember))
                .followerList(followerDtos)
                .followingList(followingDtos)
                .build();

        LibraryIntroResponse response = new LibraryIntroResponse("서재 방문 성공", detailDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 서재 소개글 수정
    @Override
    @Transactional
    public ResponseEntity<LibraryIntroUpdateResponse> updateLibraryIntro(LibraryIntroUpdateRequestDto libraryIntroUpdateRequestDto) {

        Member currnetMember = commonUtil.getMember();
        currnetMember.setIntro(libraryIntroUpdateRequestDto.getIntro());
        LibraryIntroUpdateResponse response = new LibraryIntroUpdateResponse("서재 소개글 수정 완료", true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
