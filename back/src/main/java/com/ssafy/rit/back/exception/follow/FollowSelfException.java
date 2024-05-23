package com.ssafy.rit.back.exception.follow;

public class FollowSelfException extends RuntimeException{
    public FollowSelfException() {
        super("자기 자신을 FOLLOW 할 수 없습니다.");
    }
}
