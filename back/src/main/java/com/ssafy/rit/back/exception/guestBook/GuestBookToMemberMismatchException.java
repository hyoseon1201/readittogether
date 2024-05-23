package com.ssafy.rit.back.exception.guestBook;

public class GuestBookToMemberMismatchException extends RuntimeException {

    public GuestBookToMemberMismatchException() {
        super("해당 유저는 삭제 권한이 없습니다.");
    }
}
