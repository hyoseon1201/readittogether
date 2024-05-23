package com.ssafy.rit.back.dto.postBox.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBoxListResponseDto {

    private List<ReceiveCardsDto> cards;
}
