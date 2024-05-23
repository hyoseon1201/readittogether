package com.ssafy.rit.back.exception.member;

public class CustomJWTException extends RuntimeException {
    public CustomJWTException(String msg){
        super(msg);
    }

}
