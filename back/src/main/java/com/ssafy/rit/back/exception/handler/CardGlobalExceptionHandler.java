package com.ssafy.rit.back.exception.handler;

import com.google.gson.Gson;
import com.ssafy.rit.back.exception.card.BookNotFoundException;
import com.ssafy.rit.back.exception.card.CardNotFoundException;
import com.ssafy.rit.back.exception.card.UnauthorizedCardAccessException;
import com.ssafy.rit.back.exception.card.UnauthorizedCardDeletionException;
import com.ssafy.rit.back.exception.guestBook.GuestBookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@RequiredArgsConstructor
public class CardGlobalExceptionHandler {

    private final Gson gson;
    private static final HttpHeaders JSON_HEADERS;
    static {
        JSON_HEADERS = new HttpHeaders();
        JSON_HEADERS.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFoundException(CardNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedCardAccessException.class)
    public ResponseEntity<String> handleUnauthorizedCardAccessException(UnauthorizedCardAccessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedCardDeletionException.class)
    public ResponseEntity<String> handleUnauthorizedCardDeletionException(UnauthorizedCardDeletionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(JSON_HEADERS)
                .body(stringToGson(e.getMessage()));
    }





    public String stringToGson(String message){
        return gson.toJson(Collections.singletonMap("message", message));
    }
}
