package com.ssafy.rit.back.exception.handler;

import com.google.gson.Gson;
import com.ssafy.rit.back.exception.guestBook.GuestBookNotFoundException;
import com.ssafy.rit.back.exception.guestBook.GuestBookResourceGoneException;
import com.ssafy.rit.back.exception.guestBook.GuestBookToMemberMismatchException;
import com.ssafy.rit.back.exception.member.MemberDisabledException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@RequiredArgsConstructor
public class GuestBookGlobalExceptionHandler {

    private final Gson gson;
    private static final HttpHeaders JSON_HEADERS;
    static {
        JSON_HEADERS = new HttpHeaders();
        JSON_HEADERS.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @ExceptionHandler(GuestBookResourceGoneException.class)
    public ResponseEntity<String> handleGuestBookResourceGoneException(GuestBookResourceGoneException e) {
        return ResponseEntity.status(HttpStatus.GONE)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(GuestBookNotFoundException.class)
    public ResponseEntity<String> handleGuestBookNotFoundException(GuestBookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(GuestBookToMemberMismatchException.class)
    public ResponseEntity<String> handleGuestBookToMemberMismatchException(GuestBookToMemberMismatchException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    public String stringToGson(String message){
        return gson.toJson(Collections.singletonMap("message", message));
    }
}
