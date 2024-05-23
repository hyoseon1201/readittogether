package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.postBox.requestDto.PostBoxToCardCreationRequestDto;
import com.ssafy.rit.back.dto.postBox.response.PostBoxToCardCreationResponse;
import com.ssafy.rit.back.dto.postBox.response.PostBoxListResponse;
import org.springframework.http.ResponseEntity;

public interface PostBoxService {
    ResponseEntity<PostBoxListResponse> readPostBoxList();

    ResponseEntity<PostBoxToCardCreationResponse> createPostBoxToCard(PostBoxToCardCreationRequestDto postBoxToCardCreationRequestDto);
}
