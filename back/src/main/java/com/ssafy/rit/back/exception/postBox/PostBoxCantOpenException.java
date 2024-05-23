package com.ssafy.rit.back.exception.postBox;

public class PostBoxCantOpenException extends RuntimeException {

    public PostBoxCantOpenException() {
        super("이번주에는 더이상 카드를 받을 수 없습니다.");
    }
}
