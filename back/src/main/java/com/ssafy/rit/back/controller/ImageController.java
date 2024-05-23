package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.member.responseDto.ProfileUploadResponseDto;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.serviceImpl.ImageServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
@Log4j2
public class ImageController {

    private ImageServiceImpl imageService;
    private MemberRepository memberRepository;

    @Autowired
    public ImageController(ImageServiceImpl imageService, MemberRepository memberRepository) {
        this.imageService = imageService;
        this.memberRepository = memberRepository;
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileUploadResponseDto> imageUpload(MultipartRequest request, @RequestParam String nickname) throws Exception {

        Map<String, Object> responseData = new HashMap<>();

        try {

            Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
            String s3Url = imageService.imageUpload(request, member);
            log.info("--------------------------profileImage 업뎃해줘{}--------------------------", s3Url);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);

            ProfileUploadResponseDto dto = new ProfileUploadResponseDto("profile change Success", true);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (IOException e) {

            responseData.put("uploaded", false);
            ProfileUploadResponseDto dto = new ProfileUploadResponseDto("Fail", false);

            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        }
    }

}
