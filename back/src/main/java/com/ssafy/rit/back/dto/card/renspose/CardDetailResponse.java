package com.ssafy.rit.back.dto.card.renspose;

import com.ssafy.rit.back.dto.card.responseDto.CardDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailResponse
{

    private String message;
    private CardDetailResponseDto data;

}
