package com.ssafy.rit.back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.rit.back.dto.member.requestDto.*;
import com.ssafy.rit.back.dto.member.response.PassingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingTemporaryPasswordResponse;
import com.ssafy.rit.back.dto.member.responseDto.*;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.EmailAlreadyExistsException;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.exception.member.NicknameAlreadyExistsException;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.serviceImpl.MemberServiceImpl;
import com.ssafy.rit.back.serviceImpl.MyPageServiceImpl;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@CrossOrigin("*")
@Slf4j
public class MemberController {

    ObjectMapper objectMapper = new ObjectMapper();
    private final MemberServiceImpl memberService;
    private final MemberRepository memberRepository;
    private final CommonUtil commonUtil;
    private final MyPageServiceImpl myPageService;


    public MemberController(MemberServiceImpl memberService, MemberRepository memberRepository, CommonUtil commonUtil, MyPageServiceImpl myPageService) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.commonUtil = commonUtil;
        this.myPageService = myPageService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody MemberRequestDto dto) throws JsonProcessingException {
        log.info("-------------가입 이메일: {}--------------", dto.getEmail());

        Boolean check = memberService.signUp(dto);

        if (!check) {
            SignUpResponseDto responseDto = new SignUpResponseDto("SignUp Fail. Already registered", false);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        SignUpResponseDto responseDto = new SignUpResponseDto("SignUp Success", true);
        objectMapper.writeValueAsString(responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity<CheckResponseDto> checkEmail(@RequestBody CheckEmailRequestDto dto) throws JsonProcessingException {

        log.info("------------중복 이메일 확인: {} -----------------", dto.getEmail());


        Boolean checked = memberService.checkEmail(dto);
        if (!checked) {
            log.info("-------------------이멜 중복염:{}", dto.getEmail());
            throw new EmailAlreadyExistsException();
        }
        CheckResponseDto responseDto = new CheckResponseDto("Success", true);
        objectMapper.writeValueAsString(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @PostMapping("/nickname")
    public ResponseEntity<CheckResponseDto> checkNickname(@RequestBody CheckNicknameRequestDto dto) throws JsonProcessingException {

        log.info("------------중복 닉네임 확인: {} -----------------", dto.getNickname());


        Boolean checked = memberService.checkNickname(dto);
        if (!checked) {
            log.info("-------------------닉넴 중복염:{}", dto.getNickname());
            throw new NicknameAlreadyExistsException();
        }

        CheckResponseDto responseDto = new CheckResponseDto("Success", true);
        objectMapper.writeValueAsString(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    // 회원 탈퇴
    @PostMapping("/disable")
    public ResponseEntity<DisableResponseDto> disable(@RequestBody DisableRequestDto dto) throws JsonProcessingException {

        memberService.updateDisable(dto);

        DisableResponseDto responseDto = new DisableResponseDto("Success", true);
        objectMapper.writeValueAsString(responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PutMapping("/password")
    public ResponseEntity<UpdatePasswordResponseDto> updatePassword(@RequestBody UpdatePasswordRequestDto dto) throws JsonProcessingException {

        Boolean isPossible = memberService.updatePassword(dto);

        if(!isPossible) {
            UpdatePasswordResponseDto responseDto = new UpdatePasswordResponseDto("Fail", false);
            objectMapper.writeValueAsString(responseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        UpdatePasswordResponseDto responseDto = new UpdatePasswordResponseDto("Success", true);
        objectMapper.writeValueAsString(responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/nickname")
    public ResponseEntity<UpdateNicknameResponseDto> updateNickname(@RequestBody UpdateNicknameRequestDto dto) throws JsonProcessingException {

        Member targetMember = memberRepository.findByEmail(commonUtil.getMember().getEmail()).orElseThrow(MemberNotFoundException::new);
        String oldNickname = memberService.getOldNickname(targetMember);
        Boolean isPossible = memberService.updateNickname(dto);

        if (!isPossible) {
            UpdateNicknameResponseDto responseDto = new UpdateNicknameResponseDto("Fail",false);
            objectMapper.writeValueAsString(responseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }


        UpdateNicknameResponseDto responseDto = new UpdateNicknameResponseDto("Success",true);
        objectMapper.writeValueAsString(responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        log.info("테스트입니당나라송나라");
        return "테통. 테스트 통과라는 뜻";
    }

    // 인증 메일 발송
    @PostMapping("/send-certification")
    public ResponseEntity<SendingCertificationResponse> sendEmailCertificate(@RequestBody SendingCertificationRequestDto sendingCertificationRequestDto) {
        return memberService.sendEmailCertificate(sendingCertificationRequestDto);
    }

    // 인증 코드 검증
    @PostMapping("/pass-certification")
    public ResponseEntity<PassingCertificationResponse> passEmailCertificate(@RequestBody PassingCertificationRequestDto passingCertificationRequestDto) {
        return memberService.passEmailCertificate(passingCertificationRequestDto);
    }

    // 임시비밀번호 발급
    @PostMapping("/temporary-password")
    public ResponseEntity<SendingTemporaryPasswordResponse> sendTemporaryPassword(@RequestBody SendingTemporaryPasswordRequestDto sendingTemporaryPasswordRequestDto) {
        return memberService.sendTemporaryPassword(sendingTemporaryPasswordRequestDto);
    }

    @PostMapping("/verify-access")
    public ResponseEntity<VerifyAccessResponseDto> verifyAccess(@RequestBody VerifyAccessRequestDto dto) {

        Boolean isVerified = memberService.verifyAccess(dto);
        if (!isVerified) {
            VerifyAccessResponseDto responseDto = new VerifyAccessResponseDto("failed to verified", false);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        VerifyAccessResponseDto responseDto = new VerifyAccessResponseDto("Success", true);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        // git checkout -t origin/back
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageResponseDto> myPage() {

        MyPageResponseDto responseDto = myPageService.getMyPage();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
