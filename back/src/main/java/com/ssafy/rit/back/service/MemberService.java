package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.member.requestDto.MemberRequestDto;
import com.ssafy.rit.back.dto.member.requestDto.PassingCertificationRequestDto;
import com.ssafy.rit.back.dto.member.requestDto.SendingCertificationRequestDto;
import com.ssafy.rit.back.dto.member.requestDto.SendingTemporaryPasswordRequestDto;
import com.ssafy.rit.back.dto.member.response.PassingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingTemporaryPasswordResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    Boolean signUp(MemberRequestDto dto);

    ResponseEntity<SendingCertificationResponse> sendEmailCertificate(SendingCertificationRequestDto sendingCertificationRequestDto);

    ResponseEntity<PassingCertificationResponse> passEmailCertificate(PassingCertificationRequestDto passingCertificationRequestDto);

    ResponseEntity<SendingTemporaryPasswordResponse> sendTemporaryPassword(SendingTemporaryPasswordRequestDto sendingTemporaryPasswordRequestDto);
}
