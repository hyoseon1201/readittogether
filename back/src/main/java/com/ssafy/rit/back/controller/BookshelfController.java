package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.bookshelf.requestDto.BookshelfUpdateRequestDto;
import com.ssafy.rit.back.dto.bookshelf.requestDto.BookshelfUploadRequestDto;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfDeleteResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfListResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfUpdateResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfUploadResponse;
import com.ssafy.rit.back.service.BookshelfService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookshelf")
@CrossOrigin("*")
@Slf4j
public class BookshelfController {

    private final BookshelfService bookshelfService;

    @PostMapping("/upload")
    @Operation(summary = "내 책장에 저장", description = "내 책장에 저장 (읽을 책에 저장 할 땐 0, 읽은 책에 저장 할 땐 1 입력")
    public ResponseEntity<BookshelfUploadResponse> uploadBookshelf(@RequestBody BookshelfUploadRequestDto dto){
        return bookshelfService.uploadBookshelf(dto);
    }

    @PatchMapping("/update")
    @Operation(summary = "읽은 책 읽을 책 이동", description = "읽은 책 < - > 읽을 책 서로 이동 하기")
    public ResponseEntity<BookshelfUpdateResponse> updateBookshelf(@RequestBody BookshelfUpdateRequestDto dto) {
        return bookshelfService.updateBookshelf(dto);
    }

    @DeleteMapping("/{bookshelfId}")
    @Operation( summary = "책장에서 책 삭제", description = "bookshelfId를 조회하여 삭제 합니다.")
    public ResponseEntity<BookshelfDeleteResponse> deleteBookshelf(@PathVariable("bookshelfId") Integer bookshelfId) {
        return bookshelfService.deleteBookshelf(bookshelfId);
    }

    @GetMapping("/{toMemberId}")
    @Operation(summary = "책장에 담은 책 리스트 조회", description = "유저가 책장")
    public ResponseEntity<BookshelfListResponse> readBookshelfList(@PathVariable("toMemberId") Long toMemberId,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10000") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "0") int sort,
                                                                   @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        return bookshelfService.readBookshelfList(toMemberId, page, size, sort, searchKeyword);
    }
}
