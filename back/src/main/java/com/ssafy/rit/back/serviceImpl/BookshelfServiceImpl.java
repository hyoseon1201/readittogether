package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.bookshelf.requestDto.BookshelfUpdateRequestDto;
import com.ssafy.rit.back.dto.bookshelf.requestDto.BookshelfUploadRequestDto;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfDeleteResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfListResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfUpdateResponse;
import com.ssafy.rit.back.dto.bookshelf.response.BookshelfUploadResponse;
import com.ssafy.rit.back.dto.bookshelf.responseDto.BookshelfListResponseDto;
import com.ssafy.rit.back.entity.*;
import com.ssafy.rit.back.exception.Book.BookNotFoundException;
import com.ssafy.rit.back.exception.Bookshelf.BookshelfException;
import com.ssafy.rit.back.repository.*;
import com.ssafy.rit.back.service.BookshelfService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookshelfServiceImpl implements BookshelfService {

    private final CommonUtil commonUtil;
    private final BookRepository bookRepository;
    private final BookshelfRepository bookshelfRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BookGenreRepository bookGenreRepository;


    // 책장에 책 등록하기
    @Override
    @Transactional
    public ResponseEntity<BookshelfUploadResponse> uploadBookshelf(BookshelfUploadRequestDto dto) {

        // 책 id를 받으면 내 책장에 book 정보 저장해주기
        Member currentMember = commonUtil.getMember();

        Book currentBook = bookRepository.findById(dto.getBookId()).orElseThrow(BookNotFoundException::new);


        bookshelfRepository.findByBookIdAndMemberId(currentBook, currentMember)
                .ifPresent(s -> {
                    throw BookshelfException.existException();
                });

        Optional<Comment> commentOptional = commentRepository.findByBookIdAndMemberId(currentBook, currentMember);

        int isRate = commentOptional.isPresent() ? 1 : 0;

        // 유저가 매긴 평점 받아오기 : comment 목록에 접근해서 그 책에 리뷰를 남겼나 확인하고 남겼으면 거기에 평점 가져오고 안남겼으면 0을 받아오자
        int rating = commentOptional.map(Comment::getRating).orElse(0);

        Bookshelf bookshelf = Bookshelf.builder()
                .bookId(currentBook)
                .memberId(currentMember)
                .isRead(dto.getIsRead())
                .isRate(isRate)
                .rating(rating)
                .createdAt(LocalDate.now())
                .cover(currentBook.getCover())
                .title(currentBook.getTitle())
                .build();

        bookshelfRepository.save(bookshelf);

        this.updateGroup(currentMember);

        BookshelfUploadResponse response = new BookshelfUploadResponse("책 저장에 성공했습니다.", true);


        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @Override
    @Transactional
    public ResponseEntity<BookshelfUpdateResponse> updateBookshelf(BookshelfUpdateRequestDto dto) {

        Member currentMember = commonUtil.getMember();

        Book currentBook = bookRepository.findById(dto.getBookId()).orElseThrow(BookNotFoundException::new);

        Bookshelf currentBookshelf = bookshelfRepository.findByBookIdAndMemberId(currentBook, currentMember).orElseThrow();

        int newIsRead = (currentBookshelf.getIsRead() == 1) ? 0 : 1;
        currentBookshelf.setIsRead(newIsRead);

        bookshelfRepository.save(currentBookshelf);

        this.updateGroup(currentMember);

        BookshelfUpdateResponse response = new BookshelfUpdateResponse("읽은 책, 읽을 책 으로 이동 성공", true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 책장 조회 하기
    @Override
    public ResponseEntity<BookshelfListResponse> readBookshelfList(Long toMemberId, int page, int size, int sort, String searchKeyword) {

        Pageable pageable = PageRequest.of(page, size);

        switch (sort) {
            case 0:
                pageable = PageRequest.of(page, size, Sort.by("id").descending());
                break;
            case 1:
                pageable = PageRequest.of(page, size, Sort.by("rating").descending());
                break;
            case 2:
                pageable = PageRequest.of(page, size, Sort.by("title"));
                break;
        }

        Member currentMember = memberRepository.findById(toMemberId).orElseThrow();

        Page<Bookshelf> bookshelfPage = bookshelfRepository.findAllByMemberIdAndSearchKeyword(currentMember, searchKeyword, pageable);

        int totalPage = bookshelfPage.getTotalPages();

        List<BookshelfListResponseDto> bookshelfListResponseDtos = bookshelfPage.getContent().stream()
                .map(bookshelf -> {
                    String genresStr = bookshelf.getBookId().getGenre(); // "action, mystery" 형식의 문자열을 가져옵니다.
                    List<String> genresList = Arrays.asList(genresStr.split("\\s*,\\s*")); // 쉼표와 쉼표 주변의 공백을 기준으로 문자열을 분리합니다.
                    return BookshelfListResponseDto.builder()
                            .bookId(bookshelf.getBookId().getId())
                            .title(bookshelf.getTitle())
                            .cover(bookshelf.getCover())
                            .isRead(bookshelf.getIsRead())
                            .genres(genresList)
                            .maxPage(totalPage)
                            .bookshelfId(bookshelf.getId())
                            .build();
                })
                .toList();

        BookshelfListResponse response = new BookshelfListResponse("책 조회 성공", bookshelfListResponseDtos);

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<BookshelfDeleteResponse> deleteBookshelf(Integer bookshelfId) throws BookshelfException {

        Member currentMember = commonUtil.getMember();

        Bookshelf currentBookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new BookshelfException("책장을 찾을 수 없습니다."));

        if (Objects.equals(currentBookshelf.getMemberId().getId(), currentMember.getId())) {
            bookshelfRepository.delete(currentBookshelf);
        } else {
            throw BookshelfException.notMemberException();
        }

        this.updateGroup(currentMember);

        BookshelfDeleteResponse response = new BookshelfDeleteResponse("책장에서 책 삭제 성공", true);

        return ResponseEntity.ok(response);
    }


    // 책 그룹 업데이트
    public void updateGroup(Member currentMember) {
        List<Bookshelf> bookshelfList = bookshelfRepository.findAllByMemberId(currentMember);

        List<Integer> bookIds = bookshelfList.stream()
                .filter(b -> b.getIsRead() == 1)
                .map(b -> b.getBookId().getId())
                .toList();

        List<Book> books = bookRepository.findAllByBookIds(bookIds);

        List<Integer> groupList = books.stream()
                .map(Book::getBookGroup)
                .toList();

        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        int max_v = 0;
        int max_key = currentMember.getShelfGroup();

        for (Integer group : groupList) {
            frequencyMap.put(group, frequencyMap.getOrDefault(group, 0) + 1);

            int value = frequencyMap.get(group);
            if (value >= max_v) {
                max_v = value;
                max_key = group;
            }
        }

        currentMember.setShelfGroup(max_key);
    }
}