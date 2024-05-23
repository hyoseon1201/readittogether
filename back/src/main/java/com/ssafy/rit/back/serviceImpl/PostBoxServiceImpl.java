package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.postBox.requestDto.PostBoxToCardCreationRequestDto;
import com.ssafy.rit.back.dto.postBox.response.PostBoxToCardCreationResponse;
import com.ssafy.rit.back.dto.postBox.response.PostBoxListResponse;
import com.ssafy.rit.back.dto.postBox.responseDto.PostBoxListResponseDto;
import com.ssafy.rit.back.dto.postBox.responseDto.ReceiveCardsDto;
import com.ssafy.rit.back.entity.*;
import com.ssafy.rit.back.exception.card.CardNotFoundException;
import com.ssafy.rit.back.exception.postBox.PostBoxCantOpenException;
import com.ssafy.rit.back.repository.CardRepository;
import com.ssafy.rit.back.repository.MemberRecommendBookRepository;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.repository.PostBoxRepository;
import com.ssafy.rit.back.service.PostBoxService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostBoxServiceImpl implements PostBoxService {

    private static final int POSTBOX_CARD_LIMIT = 3;

    private final CardRepository cardRepository;
    private final CommonUtil commonUtil;
    private final PostBoxRepository postBoxRepository;
    private final MemberRepository memberRepository;
    private final MemberRecommendBookRepository memberRecommendBookRepository;

    // 우편함 조회 로직
    @Override
    public ResponseEntity<PostBoxListResponse> readPostBoxList() {

        Member currentMember = commonUtil.getMember();

        if (currentMember.getIsReceivable() == 0) {
            throw new PostBoxCantOpenException();
        }

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        List<Postbox> weeklyPostboxes = postBoxRepository.findAllByMemberIdAndCreationDateBetween(currentMember, startOfWeek, endOfWeek);

        if (weeklyPostboxes.isEmpty()) {

            // 현재 멤버를 제외한 랜덤 카드 목록 가져오기
            List<Card> cards = cardRepository.findRandomCardsExcludingMember(currentMember.getId());

            // 현재 쉘프 그룹에 속한 멤버들로부터 카드 가져오기
            List<Member> allByShelfGroup = memberRepository.findAllByShelfGroup(currentMember.getShelfGroup());
            List<Card> byFromMemberIdIn = cardRepository.findByFromMemberIdInAndToMemberIdNot(allByShelfGroup, currentMember);
            if (!byFromMemberIdIn.isEmpty()) {
                Collections.shuffle(byFromMemberIdIn);
                for (Card card : byFromMemberIdIn) {
                    if (!cards.contains(card)) {
                        cards.set(0, card);
                    }
                }
            }

            // 현재 주에 해당하는 카드 목록 가져오기
            List<Card> thisWeekCards = cardRepository.findCardsBetweenDates(startOfWeek, endOfWeek, currentMember);
            if (!thisWeekCards.isEmpty()) {
                Collections.shuffle(thisWeekCards);
                for (Card card : thisWeekCards) {
                    if (!cards.contains(card)) {
                        cards.set(0, card);
                    }
                }
            }

            // 현재 멤버의 추천 도서와 연결된 카드 가져오기
            List<MemberRecommendBook> recBooks = memberRecommendBookRepository.findAllByMemberId(currentMember);
            List<Book> currentBooks = recBooks.stream().map(MemberRecommendBook::getBookId).toList();
            List<Card> byBooksAndExcludedMember = cardRepository.findByBooksAndExcludedMember(currentBooks, currentMember);
            if (!byBooksAndExcludedMember.isEmpty()) {
                Collections.shuffle(byBooksAndExcludedMember);
                for (Card card : byBooksAndExcludedMember) {
                    if (!cards.contains(card)) {
                        cards.set(0, card);
                    }
                }
            }

            List<ReceiveCardsDto> cardsDto = convertCardsToDto(cards);
            createPostboxesForCurrentMember(cards, currentMember);

            // 응답 생성 및 반환
            PostBoxListResponse response = new PostBoxListResponse("카드 조회 성공", new PostBoxListResponseDto(cardsDto));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            List<ReceiveCardsDto> cardsDto = weeklyPostboxes.stream()
                    .map(this::convertPostboxToDto)
                    .collect(Collectors.toList());

            PostBoxListResponse response = new PostBoxListResponse("카드 조회 성공", new PostBoxListResponseDto(cardsDto));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    // 우편함에 있는 카드를 내 다이어리에 저장
    @Override
    @Transactional
    public ResponseEntity<PostBoxToCardCreationResponse> createPostBoxToCard(PostBoxToCardCreationRequestDto postBoxToCardCreationRequestDto) {

        Member currentMember = commonUtil.getMember();

        Card currentCard = cardRepository.findById(postBoxToCardCreationRequestDto.getCardId())
                .orElseThrow(CardNotFoundException::new);

        Card saveCard = Card.builder()
                .comment(currentCard.getComment())
                .createdAt(LocalDate.now())
                .fromMemberId(currentCard.getFromMemberId())
                .toMemberId(currentMember)
                .bookId(currentCard.getBookId())
                .build();

        cardRepository.save(saveCard);
        currentMember.setIsReceivable(0);

        PostBoxToCardCreationResponse response = new PostBoxToCardCreationResponse("다이어리에 카드가 저장되었습니다.", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private ReceiveCardsDto convertPostboxToDto(Postbox postbox) {
        Card card = postbox.getCardId();
        return ReceiveCardsDto.builder()
                .cover(card.getBookId().getCover())
                .cardId(card.getId())
                .build();
    }

    private List<ReceiveCardsDto> convertCardsToDto(List<Card> cards) {
        return cards.stream()
                .map(card -> ReceiveCardsDto.builder()
                        .cover(card.getBookId().getCover())
                        .cardId(card.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private void createPostboxesForCurrentMember(List<Card> cards, Member currentMember) {
        cards.stream().limit(PostBoxServiceImpl.POSTBOX_CARD_LIMIT)
                .forEach(card -> {
                    Postbox newPostbox = Postbox.builder()
                            .createdAt(LocalDate.now())
                            .cardId(card)
                            .memberId(currentMember)
                            .build();
                    postBoxRepository.save(newPostbox);
                });
    }
}
