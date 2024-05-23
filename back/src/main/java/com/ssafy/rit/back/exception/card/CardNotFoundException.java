package com.ssafy.rit.back.exception.card;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {
        super("해당 카드는 존재하지 않습니다.");
    }
}
