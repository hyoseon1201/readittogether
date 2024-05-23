package com.ssafy.rit.back.exception.member;

public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException() {
        super("이미 가입하셨습니다");
    }
}
