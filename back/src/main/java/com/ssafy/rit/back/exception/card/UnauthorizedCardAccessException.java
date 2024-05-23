package com.ssafy.rit.back.exception.card;

public class UnauthorizedCardAccessException extends RuntimeException{
    public UnauthorizedCardAccessException() {
        super("권한이없는 유저가 카드를 보낼려고합니다 .");
    }
}
