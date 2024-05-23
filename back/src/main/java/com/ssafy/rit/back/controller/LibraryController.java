package com.ssafy.rit.back.controller;

import com.ssafy.rit.back.dto.library.requestDto.LibraryIntroUpdateRequestDto;
import com.ssafy.rit.back.dto.library.response.LibraryIntroResponse;
import com.ssafy.rit.back.dto.library.response.LibraryIntroUpdateResponse;
import com.ssafy.rit.back.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/{memberId}")
    public ResponseEntity<LibraryIntroResponse> readLibraryIntro(@PathVariable Long memberId) {
        return libraryService.readLibraryIntro(memberId);
    }

    @PatchMapping("/intro")
    public ResponseEntity<LibraryIntroUpdateResponse> updateLibraryIntro(@RequestBody LibraryIntroUpdateRequestDto libraryIntroUpdateRequestDto) {
        return libraryService.updateLibraryIntro(libraryIntroUpdateRequestDto);
    }
}
