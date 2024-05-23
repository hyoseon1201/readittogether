package com.ssafy.rit.back.dto.bookshelf.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookshelfDeleteResponse {

    private String message;

    private Boolean data;

}
