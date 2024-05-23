package com.ssafy.rit.back.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingTemporaryPasswordResponse {

    private String message;
    private Boolean data;
}
