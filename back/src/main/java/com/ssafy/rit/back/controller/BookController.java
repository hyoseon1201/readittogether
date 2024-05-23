package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.book.requestDto.CommentCreationRequestDto;
import com.ssafy.rit.back.dto.book.requestDto.CommentUpdateRequestDto;
import com.ssafy.rit.back.dto.book.response.*;
import com.ssafy.rit.back.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@CrossOrigin("*")
@Slf4j
public class BookController {

    private final BookService bookService;

    // 책 상세 보기
    @GetMapping("/{bookId}")
    @Operation(summary = "책 상세 조회", description = "책 상세 조회하기(코멘트 포함)")
    public ResponseEntity<BookDetailResponse> readBookDetail(@PathVariable("bookId") int bookId,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "8") int size) {
        return bookService.readBookDetail(bookId, page, size);
    }

    // 코멘트(리뷰) 관련 CRUD
    // 코메트 작성
    @PostMapping("/comment")
    @Operation(summary = "코멘트(책 리뷰) 작성 ", description = "책 코멘트(리뷰) 작성하기")
    public ResponseEntity<CommentCreationResponse> createComment(@RequestBody CommentCreationRequestDto dto){
        return bookService.createComment(dto);
    }

    // 코멘트 수정
    @PatchMapping("/comment")
    @Operation(summary = "책 코멘트 수정", description = "책 코멘트 수정")
    public ResponseEntity<CommentUpdateResponse> updateComment(@RequestBody CommentUpdateRequestDto dto) {
        return bookService.updateComment(dto);
    }

    // 코멘트 삭제
    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "책 코멘트 삭제", description = "책 코멘트 삭제")
    public ResponseEntity<CommentDeleteResponse> deleteComment(@PathVariable("commentId") Long commentId) {
        return bookService.deleteComment(commentId);
    }

}
