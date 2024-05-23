package com.ssafy.rit.back.exception.member;

public class NotCorrectNicknameException extends RuntimeException {
    public NotCorrectNicknameException() {
        super("Nickname 변경 실패");
    }

}
