package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.recommend.response.RecommendListResponse;
import com.ssafy.rit.back.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommends")
@CrossOrigin("*")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping()
    public ResponseEntity<RecommendListResponse> readRecommendList() {
        return recommendService.readRecommendList();
    }
}
