package com.ssafy.rit.back.dto.card.renspose;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateResponse {

    private String message;
    private boolean data;
}
