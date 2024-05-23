package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.member.requestDto.ImageSaveRequestDto;
import com.ssafy.rit.back.entity.Member;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    String imageUpload(MultipartRequest request, Member member) throws IOException;

}
