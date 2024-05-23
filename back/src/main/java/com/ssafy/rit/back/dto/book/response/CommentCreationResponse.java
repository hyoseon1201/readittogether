package com.ssafy.rit.back.dto.book.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreationResponse {

    private String message;

    private boolean data;
}
