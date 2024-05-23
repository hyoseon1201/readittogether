package com.ssafy.rit.back.dto.book.response;

import com.ssafy.rit.back.dto.book.responseDto.CommentListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {

    private String message;

    private List<CommentListResponseDto> comments;
}
