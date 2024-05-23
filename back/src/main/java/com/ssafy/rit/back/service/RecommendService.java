package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.recommend.response.RecommendListResponse;
import org.springframework.http.ResponseEntity;

public interface RecommendService {
    ResponseEntity<RecommendListResponse> readRecommendList();
}
