package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.card.renspose.CardCreateResponse;
import com.ssafy.rit.back.dto.card.renspose.CardDeleteResponse;
import com.ssafy.rit.back.dto.card.renspose.CardDetailResponse;
import com.ssafy.rit.back.dto.card.renspose.CardListResponse;
import com.ssafy.rit.back.dto.card.requestDto.CardCreateRequestDto;
import com.ssafy.rit.back.dto.card.requestDto.CardRequestDto;
import com.ssafy.rit.back.dto.card.responseDto.CardDetailResponseDto;
import com.ssafy.rit.back.dto.card.responseDto.CardListResponseDto;
import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.entity.Card;
import com.ssafy.rit.back.entity.Postbox;
import com.ssafy.rit.back.exception.card.BookNotFoundException;
import com.ssafy.rit.back.exception.card.CardNotFoundException;
import com.ssafy.rit.back.exception.card.UnauthorizedCardDeletionException;
import com.ssafy.rit.back.repository.*;
import com.ssafy.rit.back.service.CardService;
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
import com.ssafy.rit.back.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final BookRepository bookRepository;
    private final CommonUtil commonUtil;
    private final CardRepository cardRepository;




    @Override
    public ResponseEntity<CardDetailResponse> CardDetail(long cardId) {
        // 카드 ID로 카드 정보 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(CardNotFoundException::new);

        // 카드와 연관된 책 정보
        Book book = card.getBookId();
        if (book == null) {
            throw new BookNotFoundException();
        }

        // 보낸 사람 정보
        Member sender = card.getFromMemberId();

        // 카드 상세 정보를 담을 DTO 생성
        CardDetailResponseDto responseDto = CardDetailResponseDto.builder()
                .content(book.getInfo()) // 책 정보
                .comment(card.getComment()) // 카드 코멘트
                .title(book.getTitle()) // 책 제목
                .cover(book.getCover()) // 책 커버 이미지
                .author(book.getAuthor()) // 책 저자
                .fromId(sender.getId()) // 보낸 사람 I
                .nickname(sender.getNickname()) // 보낸 사람 닉네임.
                .Profile(sender.getProfileImage())
                .BookId(book.getId())
                .build();

        // 최종 응답 객체 생성 및 반환
        CardDetailResponse response = new CardDetailResponse("Success", responseDto);

        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<Map<String, Object>> CardList(int page, int size) {
        Member currentMember = commonUtil.getMember(); // 현재 로그인한 사용자 정보 가져오기
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());




        // 보낸 카드 목록 조회: 삭제되지 않은 카드만 필터링
        Page<Card> sentCardsPage = cardRepository.findByFromMemberIdAndDeletedBySenderIsFalse(currentMember, pageable);
        List<CardListResponseDto> sentCardDtos = sentCardsPage.getContent().stream()
                .map(card -> new CardListResponseDto(card.getId(), card.getBookId().getCover(), 0,card.getFromMemberId().getId(),card.getToMemberId().getId()))
                .collect(Collectors.toList());

        // 받은 카드 목록 조회: 삭제되지 않은 카드만 필터링
        //추가수정 보낸사람 == 받는 사람 같으면  필터링해버리기
        Page<Card> receivedCardsPage = cardRepository.findByToMemberIdAndDeletedByRecipientIsFalse(currentMember, pageable);
        List<CardListResponseDto> receivedCardDtos = receivedCardsPage.getContent().stream()
                .filter(card -> !card.getFromMemberId().equals(card.getToMemberId()))
                .map(card -> new CardListResponseDto(card.getId(), card.getBookId().getCover(), 1, card.getFromMemberId().getId(), card.getToMemberId().getId()))
                .collect(Collectors.toList());

        // 응답 객체에 페이징 정보를 포함시켜 반환
        Map<String, Object> response = new HashMap<>();
        response.put("sentCards", Map.of(
                "content", sentCardDtos,
                "totalPages", sentCardsPage.getTotalPages(),
                "totalElements", sentCardsPage.getTotalElements()
        ));
        response.put("receivedCards", Map.of(
                "content", receivedCardDtos,
                "totalPages", receivedCardsPage.getTotalPages(),
                "totalElements", receivedCardsPage.getTotalElements()
        ));

        return ResponseEntity.ok(response);
    }




    @Override
    public ResponseEntity<CardDeleteResponse> CardDelete(CardRequestDto dto) {
        Member currentMember = commonUtil.getMember(); // 현재 로그인한 사용자 정보 가져오기
        Card card = cardRepository.findById(dto.getCardId()) // 카드 ID로 카드 조회
                .orElseThrow(CardNotFoundException::new);

        // 현재 사용자가 카드의 보낸 사람인지 확인
        boolean isCurrentUserTheSender = card.getFromMemberId() != null && card.getFromMemberId().equals(currentMember);
        // 현재 사용자가 카드의 받는 사람인지 확인
        boolean isCurrentUserTheRecipient = card.getToMemberId() != null && card.getToMemberId().equals(currentMember);

        if (!isCurrentUserTheSender && !isCurrentUserTheRecipient) {
            throw new UnauthorizedCardDeletionException();
        }
        if (isCurrentUserTheSender) {
            card.setDeletedBySender(true);
        }
        if (isCurrentUserTheRecipient) {
            card.setDeletedByRecipient(true);
        }
        cardRepository.save(card);

        return ResponseEntity.ok(new CardDeleteResponse("카드 삭제 완료여.", true));
    }






    @Override
    public ResponseEntity<CardCreateResponse> CardCreate(CardCreateRequestDto dto) {
        // bookId를 통해서 책 정보 가져오기
        Book book = bookRepository.findById((int) dto.getBookId())
                .orElseThrow(BookNotFoundException::new);

        // 현재 로그인한 사용자 정보 가져오기
        Member sender = commonUtil.getMember();

        // 카드 생성 및 저장
        Card card = new Card();
        card.setComment(dto.getComment()); // 사용자가 입력한 코멘트
        card.setBookId(book); // 책 선택
        card.setFromMemberId(sender); // 현재 사용자
        card.setToMemberId(sender); // 효선이 요청
        card.setCreatedAt(LocalDate.now());
        // 랜덤한 수신자 설정 로직 삭제
        cardRepository.save(card);


        // 성공 응답 반환
        CardCreateResponse response = new CardCreateResponse("카드생성", true);
        return ResponseEntity.ok(response);
    }





}
