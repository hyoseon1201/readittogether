package com.ssafy.rit.back.serviceImpl;

import com.google.gson.JsonObject;
import com.ssafy.rit.back.entity.RefreshEntity;
import com.ssafy.rit.back.repository.RefreshRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import com.ssafy.rit.back.service.ReissueService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Service
public class ReissueServiceImpl implements ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public ReissueServiceImpl(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("there is NO refreshToken", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("NOT A RefreshToken", HttpStatus.BAD_REQUEST);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return new ResponseEntity<>("Invalid refreshToken!", HttpStatus.BAD_REQUEST);
        }


        String email = jwtUtil.getEmail(refresh);

        String newAccess = jwtUtil.createJwt("Authorization", email, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", email, 86400000L);




        // 기존의 리프레시 토큰 쿠키를 삭제
        Cookie oldRefreshCookie = createCookie("refresh", "");
        oldRefreshCookie.setPath("/");
        oldRefreshCookie.setMaxAge(0); // 유효 기간을 0으로 설정하여 삭제
        response.addCookie(oldRefreshCookie);


        // 새로운 리프레시 토큰에 대한 쿠키를 추가하고 기존의 리프레시 토큰 쿠키를 삭제
        Cookie newRefreshCookie = createCookie("refresh", newRefresh);
        newRefreshCookie.setPath("/"); // 쿠키의 경로 설정 (루트 경로로 설정)
        newRefreshCookie.setMaxAge(86400); // 쿠키의 유효 기간 설정 (초 단위, 예: 1일)
        response.addCookie(newRefreshCookie); // 새로운 쿠키를 응답에 추가




        // refresh 재발급 시 기존 DB에서 refresh 삭제 후 새로 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, 86400000L);

        response.setHeader("Authorization", newAccess);
//        response.addCookie(createCookie("refresh", newRefresh));

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        JsonObject tokenData = new JsonObject();
        tokenData.addProperty("newAccessToken", newAccess);
        tokenData.addProperty("newRefreshToken", newRefresh);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("messages", "Login success");
        jsonResponse.add("data", tokenData);

        writer.print(jsonResponse.toString());
        writer.flush();



        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setSecure(true);
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
