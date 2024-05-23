package com.ssafy.rit.back.dto.book.response;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponse {

    private String message;

    private boolean data;

}
