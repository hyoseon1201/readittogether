package com.ssafy.rit.back.dto.bookshelf.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookshelfUploadRequestDto {

    private int bookId;

    private int isRead;

}
