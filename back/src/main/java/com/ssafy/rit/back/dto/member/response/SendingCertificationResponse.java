package com.ssafy.rit.back.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendingCertificationResponse {

    private String message;
    private Boolean data;
}
