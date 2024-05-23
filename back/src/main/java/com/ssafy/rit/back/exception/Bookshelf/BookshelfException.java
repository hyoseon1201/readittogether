package com.ssafy.rit.back.exception.Bookshelf;

public class BookshelfException extends RuntimeException{

    public BookshelfException(String message) { super(message);}

    public static BookshelfException notFoundException() {
        return new BookshelfException("책장에 책이 없습니다."); }

    public static BookshelfException existException() {
        return new BookshelfException("이미 책장에 등록 되어 있습니다.");
    }

    public static BookshelfException notMemberException() {
        return new BookshelfException("삭제 권한이 없습니다.");
    }

}
