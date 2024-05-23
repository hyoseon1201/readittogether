package com.ssafy.rit.back.dto.guestBook.response;

import com.ssafy.rit.back.dto.guestBook.responseDto.GuestBookListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookListResponse {

    private String message;
    private GuestBookListResponseDto data;
}
