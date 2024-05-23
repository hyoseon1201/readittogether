package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.member.requestDto.*;
import com.ssafy.rit.back.dto.member.response.PassingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingCertificationResponse;
import com.ssafy.rit.back.dto.member.response.SendingTemporaryPasswordResponse;
import com.ssafy.rit.back.dto.member.responseDto.VerifyAccessResponseDto;
import com.ssafy.rit.back.entity.EmailCode;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.*;
import com.ssafy.rit.back.repository.EmailCodeRepository;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import com.ssafy.rit.back.service.MemberService;
import com.ssafy.rit.back.util.CommonUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonUtil commonUtil;
    private final EmailCodeRepository emailCodeRepository;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final JWTUtil jwtUtil;

    @Value("${profile.image}")
    private String basicProfileImage;

    public MemberServiceImpl(MemberRepository memberRepository,
                             PasswordEncoder passwordEncoder,
                             CommonUtil commonUtil,
                             JavaMailSender javaMailSender,
                             EmailCodeRepository emailCodeRepository,
                             RedisTemplate<String, String> redisTemplate,
                             JWTUtil jwtUtil){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.commonUtil = commonUtil;
        this.javaMailSender = javaMailSender;
        this.emailCodeRepository = emailCodeRepository;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Boolean signUp(MemberRequestDto dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();
        String name = dto.getName();
        String nickname = dto.getNickname();
        int birth = Integer.parseInt(dto.getBirth());
        int gender = dto.getGender();





        Boolean isJoined = memberRepository.existsByEmail(email);
        if (isJoined) {
            return false;
        }

        Member data = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .profileImage(basicProfileImage)
                .build();

        memberRepository.save(data);

        return true;
    }

    public Boolean checkEmail(CheckEmailRequestDto dto) {

        Optional<Member> optionalMember = memberRepository.findByEmail(dto.getEmail());
        if (optionalMember.isPresent()) {
            return false;
        }

        return true;
    }


    public Boolean checkNickname(CheckNicknameRequestDto dto) {

        Optional<Member> optionalMember = memberRepository.findByNickname(dto.getNickname());
        if (optionalMember.isPresent()) {
            return false;
        }

        return true;
    }

    public Boolean checkNewNickname(UpdateNicknameRequestDto dto) {
//        Member member = memberRepository.findByNickname(dto.getNewNickname()).orElseThrow(DuplicatedMemberException::new);
        Optional<Member> optionalMember = memberRepository.findByNickname(dto.getNewNickname());
        if (optionalMember.isPresent()) {
            return false;
        }

        return true;
    }


    // 회원 탈퇴 요청이 들어오면 비밀번호 검증
    public Member checkPasswordBeforeDisable(DisableRequestDto dto) {

        // 현재 요청을 보내는 Member
        Member currentMember = commonUtil.getMember();
        String currentEmail = currentMember.getEmail();

        // DB에서 email을 통해 찾은 Member
        Member member = memberRepository.findByEmail(currentEmail).orElseThrow(MemberNotFoundException::new);

        String rawPassword = dto.getPassword();
        String hashedPassword = member.getPassword();

        log.info("-------------------------------------------------------");
        log.info(".....................비밀번호 검증 중.....................");
        log.info("-------------------------------------------------------");

        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            return null;
        }

        return member;
    }



    @Transactional
    public Boolean updatePassword(UpdatePasswordRequestDto dto) {


        Member currentMember = commonUtil.getMember();
        Member targetMember = memberRepository.findByEmail(currentMember.getEmail()).orElseThrow(MemberNotFoundException::new);

        String inputOldPassword = dto.getOldPassword();
        String savedOldPassword = targetMember.getPassword();
        String newHashedPassword = encodePassword(dto);

        if (!passwordEncoder.matches(inputOldPassword, savedOldPassword)) {
            log.info("--------------------비밀번호 불일치--------------------");
            return false;
        }

        log.info("----------------비밀번호 일치염. 진행시켜!----------------");
        targetMember.updatePassword(newHashedPassword);


        log.info("-------------------비밀번호 변경 완료-------------------");
        return true;

    }


    @Transactional
    public void updateDisable(DisableRequestDto dto) {

        Member targetMember = checkPasswordBeforeDisable(dto);
        if (targetMember != null) {
            log.info("--------------------변경 전 상태: {}--------------------", targetMember.getIsDisabled());
            targetMember.updateDisable();
            log.info("--------------------변경 후 상태: {}--------------------", targetMember.getIsDisabled());
        }
    }


    @Transactional
    public Boolean updateNickname(UpdateNicknameRequestDto dto) {

        Member targetMember = memberRepository.findByNickname(commonUtil.getMember().getNickname()).orElseThrow(DuplicatedMemberException::new);
        Optional<Member> checkMember = memberRepository.findByNickname(dto.getNewNickname());
        if (checkMember.isPresent()) {
            return false;
        }

        log.info("------------------------------------------------------------------");
        log.info("---------------------변경 전 닉네임: {}---------------------", targetMember.getNickname());
        log.info("------------------------------------------------------------------");

        // 이전 닉과 변경하려는 닉이 동일한 경우
        if (targetMember.getNickname().equals(dto.getNewNickname())) {
            return false;
        }


        targetMember.updateNickname(dto.getNewNickname());

        log.info("---------------------변경 후 닉네임: {}---------------------", targetMember.getNickname());
        return true;

    }

    public String getOldNickname(Member member) {
        return member.getNickname();
    }


    public Boolean verifyAccess(VerifyAccessRequestDto dto) {
        log.info("=======================zxczxcxzcxzcxzxczzxczxc=====",dto) ;

        if (!dto.getAccessToken().startsWith("Bearer ")) {
            log.info(dto.getAccessToken());
            log.info("Bearer로 시작 안 함");
            return false;
        }

        String accessToken = dto.getAccessToken().split(" ")[1];
        log.info("Bearer 뗀 액세스염");

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            return false;
        }

        return true;
    }



    String encodePassword(UpdatePasswordRequestDto dto) {

        return passwordEncoder.encode(dto.getNewPassword());

    }



    // 여기부터 Redis or smtp 사용한 엔드포인트의 서비스 로직입니다.
    // TODO: 세부 예외 처리 진행 예정

    // 인증 이메일을 전송합니다.
    @Override
    public ResponseEntity<SendingCertificationResponse> sendEmailCertificate(SendingCertificationRequestDto sendingCertificationRequestDto) {

        sendVerificationEmail(sendingCertificationRequestDto.getEmail());

        SendingCertificationResponse response = new SendingCertificationResponse("인증 이메일 전송 성공", true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 인증 코드를 통해 인증 처리를 진행합니다.
    @Override
    public ResponseEntity<PassingCertificationResponse> passEmailCertificate(PassingCertificationRequestDto passingCertificationRequestDto) {
        String memberEmail = passingCertificationRequestDto.getEmail();
        int code = passingCertificationRequestDto.getCode();

        String redisKey = "email:" + memberEmail;

        // Redis 에서 가져온 코드는 String 형태일 수 있으므로, 처리를 변경
        Object storedCodeObj = redisTemplate.opsForHash().get(redisKey, "code");

        // 안전한 타입 변환을 위해 String 으로 받고, null 검사 후 정수로 변환
        if (storedCodeObj != null) {
            int storedCode = Integer.parseInt(storedCodeObj.toString());
            if (code == storedCode) {
                PassingCertificationResponse response = new PassingCertificationResponse("인증 성공", true);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        throw new EmailCodeNotMatchingException();
    }

    // 임시비밀번호를 발급합니다.
    @Override
    @Transactional
    public ResponseEntity<SendingTemporaryPasswordResponse> sendTemporaryPassword(SendingTemporaryPasswordRequestDto sendingTemporaryPasswordRequestDto) {

        Member currentMember = memberRepository.findByEmail(sendingTemporaryPasswordRequestDto.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        if (!Objects.equals(currentMember.getName(), sendingTemporaryPasswordRequestDto.getName())) {
            throw new MemberNotFoundException();
        }

        // 임시 비밀번호 생성
        String temporaryPassword = generatePassword();
        sendTemporaryPasswordEmail(currentMember.getEmail(), temporaryPassword);

        currentMember.setPassword(passwordEncoder.encode(temporaryPassword));

        SendingTemporaryPasswordResponse response = new SendingTemporaryPasswordResponse("임시 비밀번호 발송 성공", true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void sendVerificationEmail(String email) {
        // 생성된 토큰을 이용하여 이메일 본문에 포함시킬 URL 생성
        int code = generateRandomCode();

        String htmlContent = "<div style='text-align:center;'>" +
                "<h1 style='color:#446dff;'>리딧투게더 이메일 인증 확인</h1>" +
                "<p style='color:#303030; font-size:16px;'>웹에서 이메일 인증 코드를 입력하세요:</p>" +
                "<p style='font-size:18px; font-weight:bold; color:#ff0000;'>" + code + "</p>" +
                "</div>";

        sendEmailWithHtml(email, "리딧투게더 이메일 인증", htmlContent);

        emailCodeRepository.save(new EmailCode(email, code));
    }

    private void sendTemporaryPasswordEmail(String email, String newPassword) {

        String htmlContent = "<div style='text-align:center;'>" +
                "<h1 style='color:#446dff;'>리딧투게더 임시 비밀번호 발급</h1>" +
                "<p style='color:#303030; font-size:16px;'>새로운 임시 비밀번호입니다:</p>" +
                "<p style='font-size:18px; font-weight:bold; color:#ff0000;'>" + newPassword + "</p>" +
                "</div>";

        sendEmailWithHtml(email, "리딧투게더 임시 비밀번호 발급", htmlContent);
    }

    public void sendEmailWithHtml(String to, String subject, String htmlContent) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true); // 'true' indicates you're writing HTML
        };

        javaMailSender.send(messagePreparator);
    }

    private int generateRandomCode() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(900000) + 100000;
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!~#$%^&*?";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(16);

        // 숫자 1개, 소문자 1개, 특수 문자 1개를 먼저 추가합니다.
        sb.append("a1!");

        // 나머지 12개의 문자를 랜덤하게 생성합니다.
        for (int i = 0; i < 12; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }

        // 생성된 문자열을 무작위로 섞습니다.
        List<Character> charList = new ArrayList<>();
        for (char c : sb.toString().toCharArray()) {
            charList.add(c);
        }
        Collections.shuffle(charList);

        // 셔플된 문자열을 다시 합칩니다.
        StringBuilder shuffledString = new StringBuilder();
        for (char c : charList) {
            shuffledString.append(c);
        }

        return shuffledString.toString();
    }

}
