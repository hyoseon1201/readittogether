package com.ssafy.rit.back.dto.book.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreationRequestDto {

    private int bookId;

    private String comment;

    private int rating;

}
