package com.ssafy.rit.back.dto.member.responseDto;

import com.ssafy.rit.back.exception.member.AlreadyRegisteredException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpFailResponseDto {

    private AlreadyRegisteredException e;
    private Boolean data;
}
