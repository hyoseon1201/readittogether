package com.ssafy.rit.back.dto.postBox.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ReceiveCardsDto {

    private Long cardId;

    private String cover;
}
