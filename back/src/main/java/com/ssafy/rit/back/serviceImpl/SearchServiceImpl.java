package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.search.response.SearchResponse;
import com.ssafy.rit.back.dto.search.responseDto.BookDto;
import com.ssafy.rit.back.dto.search.responseDto.SearchResponseDto;
import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.repository.BookRepository;
import com.ssafy.rit.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<SearchResponse> performSearch(String query, int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        String processedQuery = Arrays.stream(query.split(""))
                .filter(s -> !s.isBlank()) // 공백 문자 제외
                .collect(Collectors.joining("%", "%", "%"));
        Page<Book> books = bookRepository.findByQueryAndSortByPubDate(processedQuery, pageable);

        List<BookDto> booksDto = books.getContent().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());

        SearchResponseDto detailDto = new SearchResponseDto(booksDto);

        SearchResponse response = new SearchResponse("검색 결과 반환 성공", detailDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
