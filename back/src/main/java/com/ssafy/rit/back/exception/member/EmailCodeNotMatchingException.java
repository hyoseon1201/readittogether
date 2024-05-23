package com.ssafy.rit.back.exception.member;

public class EmailCodeNotMatchingException extends RuntimeException {

    public EmailCodeNotMatchingException() {
        super("코드가 일치하지 않습니다.");
    }
}
