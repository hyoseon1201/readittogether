package com.ssafy.rit.back.exception.member;

public class DuplicatedMemberException extends RuntimeException{
    public DuplicatedMemberException() {
        super("이미 사용 중인 닉네임입니다");
    }
}
