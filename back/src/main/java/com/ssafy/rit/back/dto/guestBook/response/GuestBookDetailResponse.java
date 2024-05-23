package com.ssafy.rit.back.dto.guestBook.response;

import com.ssafy.rit.back.dto.guestBook.responseDto.GuestBookDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookDetailResponse {

    private String message;

    private GuestBookDetailResponseDto data;

}
