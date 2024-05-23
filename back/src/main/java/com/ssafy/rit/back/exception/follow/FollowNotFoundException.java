package com.ssafy.rit.back.exception.follow;

public class FollowNotFoundException extends RuntimeException {
    public FollowNotFoundException() {
        super("팔로우하지 않은 사용자입니다.");
    }
}
