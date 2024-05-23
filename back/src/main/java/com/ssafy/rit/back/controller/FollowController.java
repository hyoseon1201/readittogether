package com.ssafy.rit.back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.rit.back.dto.member.requestDto.FollowRequestDto;
import com.ssafy.rit.back.dto.member.responseDto.ErrorResponseDto;
import com.ssafy.rit.back.dto.member.responseDto.FollowListResponseDto;
import com.ssafy.rit.back.dto.member.responseDto.FollowMemberResponseDto;
import com.ssafy.rit.back.dto.member.responseDto.FollowResponseDto;
import com.ssafy.rit.back.entity.Follow;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.follow.AlreadyFollowedException;
import com.ssafy.rit.back.exception.follow.FollowException;
import com.ssafy.rit.back.exception.follow.FollowNotFoundException;
import com.ssafy.rit.back.exception.follow.FollowSelfException;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.serviceImpl.FollowServiceImpl;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
@CrossOrigin("*")
@Log4j2
public class FollowController {

    ObjectMapper objectMapper = new ObjectMapper();

    private final MemberRepository memberRepository;
    private final FollowServiceImpl followService;
    private final CommonUtil commonUtil;

    @Autowired
    public FollowController(MemberRepository memberRepository ,FollowServiceImpl followService, CommonUtil commonUtil) {

        this.memberRepository = memberRepository;
        this.followService = followService;
        this.commonUtil = commonUtil;
    }

    @PostMapping("/follow/{targetEmail}")
    public ResponseEntity follow(@PathVariable("targetEmail")String targetEmail, @RequestBody FollowRequestDto dto) throws JsonProcessingException {

        try {
            // 요청 보내는 사람(following)과 요청 받는 사람(follower)
            Member following = memberRepository.findById(dto.getRequestId()).orElseThrow(MemberNotFoundException::new);
            Member follower = memberRepository.findByEmail(targetEmail).orElseThrow(MemberNotFoundException::new);
            followService.follow(following, follower);

            FollowResponseDto responseDto = new FollowResponseDto("Success", true);
            objectMapper.writeValueAsString(responseDto);

            return new ResponseEntity(responseDto, HttpStatus.OK);

        } catch (FollowSelfException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto("Follow 실패!", e.getMessage());
            return new ResponseEntity(errorResponseDto, HttpStatus.BAD_REQUEST);
        } catch (AlreadyFollowedException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto("Follow 실패!", e.getMessage());
            return new ResponseEntity(errorResponseDto, HttpStatus.BAD_REQUEST);
        } catch (MemberNotFoundException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto("멤버를 찾을 수 없음!", e.getMessage());
            return new ResponseEntity(errorResponseDto, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/unfollow/{targetEmail}")
    public ResponseEntity<FollowResponseDto> unfollow(@PathVariable("targetEmail")String targetEmail, @RequestBody FollowRequestDto dto) throws JsonProcessingException {

        try {
            Member following = memberRepository.findById(dto.getRequestId()).orElseThrow(MemberNotFoundException::new);
            Member follower = memberRepository.findByEmail(targetEmail).orElseThrow(MemberNotFoundException::new);
            followService.unfollow(following, follower);

            FollowResponseDto responseDto = new FollowResponseDto("Success", true);
            objectMapper.writeValueAsString(responseDto);

            return new ResponseEntity(responseDto, HttpStatus.OK);

        } catch (FollowNotFoundException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto("Unfollow 실패!", e.getMessage());
            return new ResponseEntity(errorResponseDto, HttpStatus.BAD_REQUEST);
        } catch (MemberNotFoundException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto("멤버를 찾을 수 없음!", e.getMessage());
            return new ResponseEntity(errorResponseDto, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/following/{email}")
    public ResponseEntity<FollowListResponseDto> followingList(@PathVariable("email")String email) throws JsonProcessingException {

        Member followingOwner = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Member> followings =  followService.getFollowingList(followingOwner);

        Member currentMember = commonUtil.getMember();

        List<FollowMemberResponseDto> listResponseDtos = new ArrayList<>();
        for (Member member : followings) {
            int isFollowing = 0;
            if (followService.isFollowing(currentMember, member)) {
                isFollowing = 1;
            }
            FollowMemberResponseDto dto = new FollowMemberResponseDto(member.getId(), member.getProfileImage(), member.getNickname(), isFollowing);
            listResponseDtos.add(dto);
        }

        FollowListResponseDto responseDto = new FollowListResponseDto("Success", listResponseDtos);
        objectMapper.writeValueAsString(responseDto);


        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/follower/{email}")
    public ResponseEntity<FollowListResponseDto> followerList(@PathVariable("email")String email) throws JsonProcessingException {

        Member followerOwner = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Member> followers = followService.getFollowerList(followerOwner);

        Member currentMember = commonUtil.getMember();

        List<FollowMemberResponseDto> listResponseDtos = new ArrayList<>();
        for (Member member : followers) {
            int isFollowing = 0;
            if (followService.isFollowing(currentMember, member)) {
                isFollowing = 1;
            }
            FollowMemberResponseDto dto = new FollowMemberResponseDto(member.getId(), member.getProfileImage(), member.getNickname(),isFollowing);
            listResponseDtos.add(dto);
        }

        FollowListResponseDto responseDto = new FollowListResponseDto("Success", listResponseDtos);
        objectMapper.writeValueAsString(responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


}
