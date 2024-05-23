package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.library.requestDto.LibraryIntroUpdateRequestDto;
import com.ssafy.rit.back.dto.library.response.LibraryIntroResponse;
import com.ssafy.rit.back.dto.library.response.LibraryIntroUpdateResponse;
import org.springframework.http.ResponseEntity;

public interface LibraryService {
    ResponseEntity<LibraryIntroResponse> readLibraryIntro(Long memberId);

    ResponseEntity<LibraryIntroUpdateResponse> updateLibraryIntro(LibraryIntroUpdateRequestDto libraryIntroUpdateRequestDto);
}
