package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.kakao.TokenResponse;
import com.ssafy.rit.back.dto.member.responseDto.DataDto;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import com.ssafy.rit.back.service.KakaoLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";

    @Value("${kakao.auth.url}")
    private String kakaoAuthReqUrl;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${profile.image}")
    private String profileImage;

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private final MemberRepository memberRepository;

    public KakaoLoginServiceImpl(RestTemplate restTemplate, MemberRepository memberRepository) {
        this.restTemplate = restTemplate;
        this.memberRepository = memberRepository;
    }


    @Override
    public DataDto processAuthorizationCode(String code, HttpServletResponse response) throws IOException {
        // 카카오로부터 받은 인가 코드를 사용하여 액세스 토큰 요청 등 처리
        // restTemplate 사용하여 카카오 API 호출
        // 필요한 사용자 정보 얻어오기
        log.info("----------------------------------------------");
        log.info("======인가 코드: {}===========", code);
        log.info("----------------------------------------------");

        TokenResponse tokenResponse = requestToken(code);
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();

        log.info("----------------------------------------------");
        log.info("======액세스 토큰: {}===========", accessToken);
        log.info("======리프레시 토큰: {}===========", refreshToken);
        log.info("----------------------------------------------");

        Map<String, Object> kakaoAccount = requestUser(accessToken);

        String kakaoEmail = (String)kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String kakaoName = (String) profile.get("nickname");
        String kakaoProfileImage = (String) profile.get("profile_image_url");
        if (kakaoProfileImage == null) {
            kakaoProfileImage = profileImage;
        }

        log.info("이메일염: {}", kakaoEmail);
        log.info("이름염: {}", kakaoName);


        Member member = memberRepository.findByEmail(kakaoEmail).orElse(null);
        String nickname = null;

        String randomNickname = generateRandomNickname();
        // 기존 회원인 경우
        if (member != null) {
            nickname = member.getNickname();
            Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthTokens authTokens = processLogin(response, member);
            accessToken = authTokens.getAccessToken();
            refreshToken = authTokens.getRefreshToken();


        } else {    // 신규 회원인 경우

            Member newMember = new Member();
            newMember.setName(kakaoName);
            newMember.setEmail(kakaoEmail);
            newMember.setNickname(randomNickname);
            newMember.setProfileImage(kakaoProfileImage);
            // 신규 회원 등록
            memberRepository.save(newMember);

            nickname = randomNickname;
            // 회원 등록 후 로그인 처리 및 토큰 전달
            AuthTokens authTokens = processLogin(response, newMember);
            accessToken = authTokens.getAccessToken();
            refreshToken = authTokens.getRefreshToken();
        }

        Member memberForId = memberRepository.findByEmail(kakaoEmail).orElse(null);
        Long memberId = null;
        if (memberForId != null) {
            memberId = memberForId.getId();
        }

        System.out.println(response);
        System.out.println(refreshToken);

        return new DataDto(accessToken, refreshToken, memberId, kakaoProfileImage, nickname);

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AuthTokens {
        private String accessToken;
        private String refreshToken;
    }



    public String getAuthorizationUrl() {
        String authUrl = kakaoAuthReqUrl +
                "?client_id=" + kakaoClientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code";


        return authUrl;
    }

    @Autowired
    private JWTUtil jwtUtil;

    public AuthTokens processLogin(HttpServletResponse response, Member member) {
        // 우리 시스템의 JWT 액세스 토큰과 리프레시 토큰 생성
        String accessToken = jwtUtil.createJwt("Authorization", member.getEmail(), 60000000L); // 예시 유효 시간
        String refresh = jwtUtil.createJwt("Refresh", member.getEmail(), 259000000L);
        System.out.println(accessToken);

        // 생성된 토큰을 클라이언트에게 반환
        response.setHeader("Authorization", "Bearer " + accessToken);
        Cookie refreshTokenCookie = new Cookie("refresh", refresh);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60); // 30일
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        return new AuthTokens(accessToken, refresh);



    }


    // 무작위 닉네임 생성 메서드
    private String generateRandomNickname() {
        SecureRandom random = new SecureRandom();
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append((char) (random.nextInt(26) + 'a'));
            }
            String randomNickname = sb.toString();

            if (!memberRepository.existsByNickname(randomNickname)) {
                return randomNickname;
            }
        }
    }




    // 인가 코드를 통해 액세스/리프레시 토큰 요청하기
    private TokenResponse requestToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String requestBody = String.format("grant_type=authorization_code&client_id=%s&redirect_uri=%s&code=%s",
                kakaoClientId, redirectUri, code);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                request,
                TokenResponse.class
        );

        TokenResponse tokenResponse = response.getBody();
        if (tokenResponse != null) {
            return tokenResponse;
        } else {
            throw new RuntimeException("Failed to get tokens");
        }
    }


    private LinkedHashMap<String, Object> requestUser(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_USERINFO_URL).build();
        ResponseEntity<Map> response = restTemplate.exchange(
                uriBuilder.toString(),
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> bodyMap = response.getBody();
        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) bodyMap.get("kakao_account");
        log.info(kakaoAccount);
        log.info("=========kakaoAccount: {}=========", kakaoAccount);

        return kakaoAccount;

    }
}
