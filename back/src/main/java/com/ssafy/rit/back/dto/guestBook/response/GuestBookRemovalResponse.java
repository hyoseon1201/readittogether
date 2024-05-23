package com.ssafy.rit.back.dto.guestBook.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookRemovalResponse {

    private String message;

    private boolean data;

}
