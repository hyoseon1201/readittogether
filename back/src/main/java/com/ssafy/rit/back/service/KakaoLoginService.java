package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.member.responseDto.DataDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface KakaoLoginService {
    DataDto processAuthorizationCode(String code, HttpServletResponse response) throws IOException;

}
