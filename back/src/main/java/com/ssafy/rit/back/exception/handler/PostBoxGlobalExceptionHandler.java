package com.ssafy.rit.back.exception.handler;

import com.google.gson.Gson;
import com.ssafy.rit.back.exception.postBox.PostBoxCantOpenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice //전역처리
@RequiredArgsConstructor
public class PostBoxGlobalExceptionHandler {

    private final Gson gson;
    private static final HttpHeaders JSON_HEADERS;
    static {
        JSON_HEADERS = new HttpHeaders();
        JSON_HEADERS.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }
    @ExceptionHandler(PostBoxCantOpenException.class)
    public ResponseEntity<String> handlePostBoxCantOpenException(PostBoxCantOpenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    public String stringToGson(String message){
        return gson.toJson(Collections.singletonMap("message", message));
    }
}
