package com.ssafy.rit.back.exception.member;

public class MemberDisabledException extends RuntimeException {

    public MemberDisabledException() {
        super("해당 유저는 탈퇴했습니다.");
    }
}
