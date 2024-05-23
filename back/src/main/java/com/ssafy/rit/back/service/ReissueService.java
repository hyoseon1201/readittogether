package com.ssafy.rit.back.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReissueService {
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
