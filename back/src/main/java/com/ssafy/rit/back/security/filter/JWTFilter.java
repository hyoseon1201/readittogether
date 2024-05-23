package com.ssafy.rit.back.security.filter;

import com.ssafy.rit.back.dto.member.customDto.CustomUserDetails;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JWTFilter extends OncePerRequestFilter  {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.debug("-----문제있다이거-----{}----------", authorization);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];


        // 액세스 토큰 만료 체크
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            writer.println("---------------------------------");
            writer.println("accessToken expired");
            writer.println("---------------------------------");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 액세스 토큰인지 확인
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("Authorization")) {
            PrintWriter writer = response.getWriter();
            writer.print("IT'S NOT A ACCESSTOKEN");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtUtil.getEmail(accessToken);


        log.info("--------------{}------------",accessToken);


        Member member = Member.builder()
                .email(email)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        // customUserDetails 사용해서 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, null);
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
