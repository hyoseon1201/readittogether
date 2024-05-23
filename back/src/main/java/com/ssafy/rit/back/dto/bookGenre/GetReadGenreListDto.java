package com.ssafy.rit.back.dto.bookGenre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetReadGenreListDto {

    private int genreId;
    private int genreCount;
}
