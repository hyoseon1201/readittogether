package com.ssafy.rit.back.exception.handler;

import com.google.gson.Gson;
import com.ssafy.rit.back.exception.Book.BookNotFoundException;
import com.ssafy.rit.back.exception.Book.CommentException;
import com.ssafy.rit.back.exception.guestBook.GuestBookToMemberMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice //전역처리
@RequiredArgsConstructor
public class BookGlobalExceptionHandler {

    private final Gson gson;
    private static final HttpHeaders JSON_HEADERS;
    static {
        JSON_HEADERS = new HttpHeaders();
        JSON_HEADERS.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(CommentException.class) //예외처리 핸들러
    public ResponseEntity<String> handleCommentException(CommentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    public String stringToGson(String message){
        return gson.toJson(Collections.singletonMap("message", message));
    }
}
