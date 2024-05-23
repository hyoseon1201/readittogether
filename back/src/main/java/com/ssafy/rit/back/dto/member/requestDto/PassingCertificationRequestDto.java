package com.ssafy.rit.back.dto.member.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassingCertificationRequestDto {

    private String email;
    private int code;
}
