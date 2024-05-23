package com.ssafy.rit.back.exception.member;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberWrongEmailException extends UsernameNotFoundException {

    public MemberWrongEmailException(String msg) {
        super(msg);
    }
}
