package com.ssafy.rit.back.exception.card;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException() {
        super("해당 책이 존재하지 않습니다.");
    }

}
