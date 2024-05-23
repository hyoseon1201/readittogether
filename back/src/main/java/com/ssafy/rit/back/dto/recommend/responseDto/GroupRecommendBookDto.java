package com.ssafy.rit.back.dto.recommend.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRecommendBookDto {

    private String cover;
    private String title;
    private int bookId;
}
