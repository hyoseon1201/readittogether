package com.ssafy.rit.back.dto.book.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private int bookId;

    private Long commentId;

    private String comment;

    private int rating;

}
