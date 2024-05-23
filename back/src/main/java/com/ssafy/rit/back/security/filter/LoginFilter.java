package com.ssafy.rit.back.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ssafy.rit.back.dto.member.responseDto.SignInResponseDto;
import com.ssafy.rit.back.dto.member.responseDto.DataDto;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.entity.RefreshEntity;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.repository.RefreshRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.aot.hint.annotation.ReflectiveRuntimeHintsRegistrar;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Log4j2
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository, MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("-----------로그인 시도 중---------");
        try {

            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(request.getInputStream());

            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();

            Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
            if (member.getIsDisabled() == 1) {
                response.setStatus(401);
                log.info("--------------Disabled된 유저염--------------");
                return null;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
            return authenticationManager.authenticate(authToken);


        } catch (Exception e) {
            log.info("--------------------------------걸림--------------------------------");
            response.setStatus(401);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Long memberId = member.getId();
        String profileImage = member.getProfileImage();
        String nickname = member.getNickname();


        String accessToken = jwtUtil.createJwt("Authorization", email, 6000000L);
        String refreshToken = jwtUtil.createJwt("refresh", email, 86400000L);

        // refreshToken를 서버에 저장
        addRefreshEntity(email, refreshToken, 86400000L);

        response.setHeader("Authorization", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json; charset=UTF-8");

        log.info("-----------------------로그인 완료염-----------------------");

        DataDto dataDto = new DataDto(accessToken, refreshToken, memberId, profileImage, nickname);
        SignInResponseDto responseDto = new SignInResponseDto("Login Success", dataDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseDto);

        response.getWriter().write(jsonResponse);


        /*

        PrintWriter writer = response.getWriter();

        // 로그인 성공 시 프론트에게 토큰 전달(json형식)
        JsonObject tokenData = new JsonObject();
        tokenData.addProperty("accessToken", accessToken);
        tokenData.addProperty("refreshToken", refreshToken);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Login success");
        jsonResponse.add("data", tokenData);

        writer.print(jsonResponse.toString());
        writer.flush();

        */


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

       response.setStatus(401);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorMessage = "Not Found User";
        int statusCode = 404;


        // 실패 시 적절한 상태 코드 반환
        response.setStatus(statusCode);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("errorMessage", errorMessage);

        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.flush();

        // 실패 시 적절한 상태 코드 반환
        response.setStatus(statusCode);
    }


    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }


    private void addRefreshEntity(String email, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                        .email(email)
                                .refresh(refresh)
                                        .expiration(date.toString())
                                                .build();

        refreshRepository.save(refreshEntity);
    }

}
