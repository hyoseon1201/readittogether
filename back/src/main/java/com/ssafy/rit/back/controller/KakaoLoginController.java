package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.member.responseDto.DataDto;
import com.ssafy.rit.back.dto.member.responseDto.SignInResponseDto;
import com.ssafy.rit.back.serviceImpl.KakaoLoginServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

@RestController
@CrossOrigin("*")
public class KakaoLoginController {

    @Value("${cors.allowed-origins}")
    private String backUrl;

    private KakaoLoginServiceImpl kakaoLoginService;

    public KakaoLoginController(KakaoLoginServiceImpl kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }


    @GetMapping("/klogin")
    public ResponseEntity<String> kakaoLogin() {
        System.out.println("get이라고");
        String redirectUri = kakaoLoginService.getAuthorizationUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUri).build();
    }

    @GetMapping("/login/oauth2/code/kakao")
    public void handleCallback(@RequestParam("code")String code, HttpServletResponse response) throws LoginException, IOException {
        // 카카오로부터 받은 인가 코드 처리
        DataDto datadto = kakaoLoginService.processAuthorizationCode(code, response);
        SignInResponseDto responseDto = new SignInResponseDto("Login Success", datadto);
        Long memberId = datadto.getMemberId();
        String nickname = datadto.getNickname();
        String accessToken = datadto.getAccessToken();
        String refreshToken = datadto.getRefreshToken();
        String profileImage = datadto.getProfileImage();


        /*
        response.sendRedirect(backUrl
                + "/ktest?memberId=" + memberId
                + "&nickname=" + nickname
                + "&accessToken=" + accessToken
                + "&refreshToken=" + refreshToken
                + "&profileImage=" + profileImage);

         */

        // UriComponentsBuilder를 사용하여 URL 생성
        String redirectUrl = UriComponentsBuilder.fromHttpUrl(backUrl)
                .path("/ktest")
                .queryParam("memberId", memberId)
                .queryParam("nickname", nickname)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .queryParam("profileImage", profileImage)
                .toUriString();

        // 생성된 URL 출력
        System.out.println("Redirect URL: " + redirectUrl);

        response.sendRedirect(redirectUrl);

    }

    @GetMapping("/ktest")
    public String handleGetRequest() {
        return "다음 작업 수행 ㄱㄱ";
    }


    /* 프론트 예시 코드
    // 현재 URL에서 쿼리 파라미터를 추출하는 함수
    function getQueryParameters() {
        var queryParams = {};
        var queryString = window.location.search.substring(1);
        var pairs = queryString.split("&");
        for (var i = 0; i < pairs.length; i++) {
            var pair = pairs[i].split("=");
            var key = decodeURIComponent(pair[0]);
            var value = decodeURIComponent(pair[1]);
            queryParams[key] = value;
        }
        return queryParams;
    }

    // 쿼리 파라미터를 받아와서 사용하는 예시
    var queryParams = getQueryParameters();
    var memberId = queryParams["memberId"];
    var nickname = queryParams["nickname"];
    var accessToken = queryParams["accessToken"];
    var refreshToken = queryParams["refreshToken"];
    var profileImage = queryParams["profileImage"];

// 이후에 받은 데이터를 사용하여 필요한 작업을 수행할 수 있습니다.

     */

}
