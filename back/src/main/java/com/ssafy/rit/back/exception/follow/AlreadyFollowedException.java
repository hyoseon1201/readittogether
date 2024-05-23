package com.ssafy.rit.back.exception.follow;

public class AlreadyFollowedException extends RuntimeException{

    public AlreadyFollowedException() {
        super("이미 FOLLOW 한 사용자입니다.");
    }
}
