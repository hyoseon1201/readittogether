package com.ssafy.rit.back.exception.Book;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() { super("해당 책은 존재하지 않습니다.");}
}
