package com.ssafy.rit.back.dto.bookshelf.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookshelfUpdateRequestDto {

    private int bookId;

}
