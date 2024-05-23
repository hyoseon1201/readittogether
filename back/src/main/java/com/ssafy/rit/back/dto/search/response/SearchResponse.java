package com.ssafy.rit.back.dto.search.response;

import com.ssafy.rit.back.dto.search.responseDto.SearchResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private String message;

    private SearchResponseDto data;
}
