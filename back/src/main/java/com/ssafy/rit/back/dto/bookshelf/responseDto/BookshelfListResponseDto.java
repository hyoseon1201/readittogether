package com.ssafy.rit.back.dto.bookshelf.responseDto;

import com.ssafy.rit.back.entity.BookGenre;
import com.ssafy.rit.back.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookshelfListResponseDto {

    private int bookId;

    private String title;

    private String cover;

    private int isRead;

    private int rating;

    private List<String> genres;

    private int maxPage;

    private int bookshelfId;

}
