package com.ssafy.rit.back.dto.card.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CardDetailResponseDto {

    private String content;
    private String comment;
    private String title;
    private String cover;
    private String author;
    private long fromId;
    private String nickname;

    private long BookId;
    private String Profile;


}