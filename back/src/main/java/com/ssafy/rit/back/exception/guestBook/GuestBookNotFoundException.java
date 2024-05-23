package com.ssafy.rit.back.exception.guestBook;

public class GuestBookNotFoundException extends RuntimeException {

    public GuestBookNotFoundException() {
        super("해당 방명록은 존재하지 않습니다.");
    }
}
