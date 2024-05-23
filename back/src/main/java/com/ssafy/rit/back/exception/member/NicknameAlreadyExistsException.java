package com.ssafy.rit.back.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "nickname already exists")
public class NicknameAlreadyExistsException extends RuntimeException {

    public NicknameAlreadyExistsException() {
        super("Duplicated Nickname");
    }
}
