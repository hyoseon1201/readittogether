package com.ssafy.rit.back.dto.guestBook.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookCreationRequestDto {

    private String content;
}
