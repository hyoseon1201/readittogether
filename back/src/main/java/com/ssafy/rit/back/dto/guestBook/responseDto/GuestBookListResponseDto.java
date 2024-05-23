package com.ssafy.rit.back.dto.guestBook.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GuestBookListResponseDto {

    List<Long> guestbookList;
}
