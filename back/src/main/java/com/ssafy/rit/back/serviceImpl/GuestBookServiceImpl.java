package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.guestBook.requestDto.GuestBookCreationRequestDto;
import com.ssafy.rit.back.dto.guestBook.response.GuestBookCreationResponse;
import com.ssafy.rit.back.dto.guestBook.response.GuestBookDetailResponse;
import com.ssafy.rit.back.dto.guestBook.response.GuestBookListResponse;
import com.ssafy.rit.back.dto.guestBook.response.GuestBookRemovalResponse;
import com.ssafy.rit.back.dto.guestBook.responseDto.GuestBookDetailResponseDto;
import com.ssafy.rit.back.dto.guestBook.responseDto.GuestBookListResponseDto;
import com.ssafy.rit.back.entity.GuestBook;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.guestBook.GuestBookNotFoundException;
import com.ssafy.rit.back.exception.guestBook.GuestBookResourceGoneException;
import com.ssafy.rit.back.exception.guestBook.GuestBookToMemberMismatchException;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.GuestBookRepository;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.service.GuestBookService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final MemberRepository memberRepository;
    private final CommonUtil commonUtil;
    private final ModelMapper modelMapper;

    // 방명록 생성
    @Override
    public ResponseEntity<GuestBookCreationResponse> createGuestBook(Long toMemberId, GuestBookCreationRequestDto dto) {

        // 현재 접속 유저 정보를 조회합니다. (비활성화 유저나 존재하지 않는 유저인 경우 예외 처리는 CommonUtil 클래스 내 getMember 내에서 처리)
        Member currentMember = commonUtil.getMember();

        // 어느 유저의 방명록에 작성하는지 toMember 를 PathVariable 로 받은 toMemberId로 조회합니다. 존재하지 않는 유저의 방명록일 경우 예외처리
        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(MemberNotFoundException::new);

        // 방명록 주인이 탈퇴한 유저일 경우 리소스 접근 불가 예외처리
        if (toMember.getIsDisabled() == 1) {
            throw new GuestBookResourceGoneException();
        }

        // 방명록 객체 생성 후 저장
        GuestBook newGuestBook = modelMapper.map(dto, GuestBook.class);
        newGuestBook.setToMemberId(toMember);
        newGuestBook.setFromMemberId(currentMember);
        newGuestBook.setCreatedAt(LocalDate.now());

        guestBookRepository.save(newGuestBook);

        // response 내에 dto를 넣어서 반환. 방명록 작성과 같이 작성 성공만 반환하면 될 경우 true 를 반환합니다.
        GuestBookCreationResponse response = new GuestBookCreationResponse("방명록 작성 성공", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 방명록 조회
    @Override
    public ResponseEntity<GuestBookDetailResponse> readGuestBookDetail(Long postId) {

        commonUtil.getMember();

        GuestBook currentGuestBook = guestBookRepository.findById(postId)
                .orElseThrow(GuestBookNotFoundException::new);

        GuestBookDetailResponseDto detailDto = GuestBookDetailResponseDto.builder()
                .content(currentGuestBook.getContent())
                .nickname(currentGuestBook.getFromMemberId().getNickname())
                .fromMemberId(currentGuestBook.getFromMemberId().getId())
                .profileImg(currentGuestBook.getFromMemberId().getProfileImage())
                .createdAt(String.valueOf(currentGuestBook.getCreatedAt()))
                .build();

        GuestBookDetailResponse response = new GuestBookDetailResponse("방명록 조회 성공", detailDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 방명록 삭제
    @Override
    public ResponseEntity<GuestBookRemovalResponse> deleteGuestBook(Long postId) {

        Member currentMember = commonUtil.getMember();

        GuestBook currentGuestBook = guestBookRepository.findById(postId)
                .orElseThrow(GuestBookNotFoundException::new);

        if (currentGuestBook.getFromMemberId() != currentMember && currentGuestBook.getToMemberId() != currentMember) {
            throw new GuestBookToMemberMismatchException();
        }

        guestBookRepository.delete(currentGuestBook);

        GuestBookRemovalResponse response = new GuestBookRemovalResponse("방명록 삭제 성공", true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 해당 유저 방명록 리스트 조회
    @Override
    public ResponseEntity<GuestBookListResponse> readGuestBookList(Long toMemberId) {

        commonUtil.getMember();

        Member currentMember = memberRepository.findById(toMemberId)
                .orElseThrow(MemberNotFoundException::new);

        List<Long> guestBookIds = guestBookRepository.findIdsByToMemberId(currentMember);

        GuestBookListResponseDto detailDto = GuestBookListResponseDto.builder()
                .guestbookList(guestBookIds)
                .build();

        GuestBookListResponse response = new GuestBookListResponse(
                "방명록 목록 조회 성공",
                detailDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
