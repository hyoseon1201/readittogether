package com.ssafy.rit.back.dto.guestBook.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookDetailResponseDto {

    private Long fromMemberId;
    private String profileImg;
    private String nickname;
    private String content;
    private String createdAt;

}
