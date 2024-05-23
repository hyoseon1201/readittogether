package com.ssafy.rit.back.dto.postBox.response;

import com.ssafy.rit.back.dto.postBox.responseDto.PostBoxListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBoxListResponse {

    private String message;

    private PostBoxListResponseDto data;
}
