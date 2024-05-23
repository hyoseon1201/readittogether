package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.search.response.SearchResponse;
import com.ssafy.rit.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@CrossOrigin("*")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("")
    public ResponseEntity<SearchResponse> search(@RequestParam("q") String query, @RequestParam("page") int page) {
        return searchService.performSearch(query, page);
    }
}
