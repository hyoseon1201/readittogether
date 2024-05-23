package com.ssafy.rit.back.controller;

import com.google.gson.JsonObject;
import com.ssafy.rit.back.repository.RefreshRepository;
import com.ssafy.rit.back.security.jwt.JWTUtil;
import com.ssafy.rit.back.serviceImpl.ReissueServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/members")
@RestController
public class ReissueController {

    private final ReissueServiceImpl reissueService;


    @Autowired
    public ReissueController(ReissueServiceImpl reissueService) {
        this.reissueService = reissueService;
    }

    @PostMapping("/reissue")            // POST로 none 요청 보내야 함
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return reissueService.reissueToken(request, response);
    }


}
