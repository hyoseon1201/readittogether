package com.ssafy.rit.back.service;

import com.ssafy.rit.back.dto.card.renspose.CardCreateResponse;
import com.ssafy.rit.back.dto.card.renspose.CardDeleteResponse;
import com.ssafy.rit.back.dto.card.renspose.CardDetailResponse;
import com.ssafy.rit.back.dto.card.renspose.CardListResponse;
import com.ssafy.rit.back.dto.card.requestDto.CardCreateRequestDto;
import com.ssafy.rit.back.dto.card.requestDto.CardRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface CardService {

    ResponseEntity<CardDetailResponse> CardDetail(long cardId);
    ResponseEntity<Map<String, Object>> CardList(int page, int size); // 수정된 부분
    ResponseEntity<CardDeleteResponse> CardDelete(CardRequestDto dto);
    ResponseEntity<CardCreateResponse> CardCreate(CardCreateRequestDto dto);



}
