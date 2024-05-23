package com.ssafy.rit.back.security.filter;

import com.ssafy.rit.back.repository.RefreshRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);

    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^/logout$")) {

            filterChain.doFilter(request, response);

            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        // refreshToken 얻기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }


        if (refresh == null ) {
            System.out.println("--------------로가웃 경로 에러염4--------------" + refresh);
            System.out.println("--------------로가웃 경로 에러염4--------------" + refresh);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equalsIgnoreCase("refresh")) {


            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

//        Boolean isExist = refreshRepository.existsByRefresh(refresh);
//        System.out.println("--------------로가웃 경로 에러염555--------------" + isExist);
//
//        if (!isExist) {
//            System.out.println("--------------로가웃 경로 에러염5--------------" + requestUri);
//            System.out.println("--------------로가웃 경로 에러염5--------------" + requestUri);
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }

        System.out.println("-------------------굿 로그아웃 진행 ㄱㄱ-------------------" + requestUri);
        // 로그아웃 진행 - DB에서 리프레시 토큰 제거, 리프레시 토큰의 쿠키값 초기화
        refreshRepository.deleteByRefresh(refresh);

        System.out.println("-------------------굿 로그아웃 진행 ㄱㄱ-------------------" + refresh);

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //by 복씨
//        cookie.setSecure(true); // HTTPS 환경에서 필수
//        cookie.setHttpOnly(true);
//        cookie.setDomain("j10d206.p.ssafy.io");

        Cookie c = new Cookie("refresh", null);
        c.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);


    }
}
