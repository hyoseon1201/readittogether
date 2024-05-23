package com.ssafy.rit.back.exception.guestBook;

public class GuestBookResourceGoneException extends RuntimeException {

    public GuestBookResourceGoneException() {
        super("탈퇴한 유저의 방명록 입니다.");
    }
}
