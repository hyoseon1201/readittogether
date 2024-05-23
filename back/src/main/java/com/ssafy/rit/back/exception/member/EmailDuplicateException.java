package com.ssafy.rit.back.exception.member;

public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException() {
        super("이메일이 중복되었습니다.");
    }
}
