package com.ssafy.rit.back.dto.card.renspose;

import com.ssafy.rit.back.dto.card.responseDto.CardListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardListResponse {

    private String message;
    private List<CardListResponseDto> data;

}
