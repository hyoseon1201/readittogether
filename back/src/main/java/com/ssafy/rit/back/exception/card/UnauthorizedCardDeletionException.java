package com.ssafy.rit.back.exception.card;

public class UnauthorizedCardDeletionException extends RuntimeException{
    public UnauthorizedCardDeletionException() {
        super("권한이없는 유저가 카드를 삭제할려고합니다.");
    }
}
