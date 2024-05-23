package com.ssafy.rit.back.dto.library.response;

import com.ssafy.rit.back.dto.library.responseDto.LibraryIntroResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryIntroResponse {

    private String message;

    private LibraryIntroResponseDto data;
}
