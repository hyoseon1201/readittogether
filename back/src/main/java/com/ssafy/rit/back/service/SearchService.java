package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.search.response.SearchResponse;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    ResponseEntity<SearchResponse> performSearch(String query, int page);
}
