package com.ssafy.rit.back.dto.recommend.response;

import com.ssafy.rit.back.dto.recommend.responseDto.RecommendListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendListResponse {

    private String message;
    private RecommendListResponseDto data;
}
