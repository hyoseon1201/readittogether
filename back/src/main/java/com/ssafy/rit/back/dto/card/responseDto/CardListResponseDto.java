package com.ssafy.rit.back.dto.card.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardListResponseDto {
    private long cardId;
    private String cover;
    private int isWrite;
    private long from_Member;
    private long to_Member;
}

