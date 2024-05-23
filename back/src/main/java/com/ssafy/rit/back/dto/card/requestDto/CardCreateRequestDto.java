package com.ssafy.rit.back.dto.card.requestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequestDto {
    private String content;
    private String comment;
    private long bookId;

}
