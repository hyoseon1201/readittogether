package com.ssafy.rit.back.dto.bookshelf.response;

import com.ssafy.rit.back.dto.bookshelf.responseDto.BookshelfListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookshelfListResponse {

    private String message;

    private List<BookshelfListResponseDto> data;

}
