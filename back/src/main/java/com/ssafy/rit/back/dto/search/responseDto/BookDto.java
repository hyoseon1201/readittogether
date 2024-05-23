package com.ssafy.rit.back.dto.search.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int bookId;
    private String cover;
    private String title;
    private String author;
    private Integer rating;
    private String pubDate;
}