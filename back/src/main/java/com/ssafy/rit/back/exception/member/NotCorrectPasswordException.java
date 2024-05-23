package com.ssafy.rit.back.exception.member;

public class NotCorrectPasswordException extends RuntimeException {
    public NotCorrectPasswordException() {
        super("Password 불일치");
    }
}
