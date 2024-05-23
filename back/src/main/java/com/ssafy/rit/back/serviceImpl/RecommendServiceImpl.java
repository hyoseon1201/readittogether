package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.recommend.response.RecommendListResponse;
import com.ssafy.rit.back.dto.recommend.responseDto.RecommendListResponseDto;
import com.ssafy.rit.back.entity.GroupRecommendBook;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.entity.MemberRecommendBook;
import com.ssafy.rit.back.repository.GroupRecommendBookRepository;
import com.ssafy.rit.back.repository.MemberRecommendBookRepository;
import com.ssafy.rit.back.service.RecommendService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final CommonUtil commonUtil;
    private final GroupRecommendBookRepository groupRecommendBookRepository;
    private final MemberRecommendBookRepository memberRecommendBookRepository;

    @Override
    public ResponseEntity<RecommendListResponse> readRecommendList() {
        Member currentMember = commonUtil.getMember();

        // 현재 유저 나이대 및 성별 조회 -> 해당 그룹 추천 리스트 가져오기
        int birth = currentMember.getBirth();
        int gender = currentMember.getGender();
        int assignGroup = assignGroup(birth, gender);
        List<GroupRecommendBook> ageAndGenderGroupRecommendBookList = groupRecommendBookRepository.findAllByReGroup(assignGroup);

        // 현재 유저 개인 추천 리스트 조회 -> 개인 추천 리스트 가져오기
        List<MemberRecommendBook> memberRecommendBookList = memberRecommendBookRepository.findAllByMemberId(currentMember);

        // 현재 유저 콘텐츠 기반 그룹 추천 리스트 조회 -> 해당 그룹 추천 리스트 가져오기
        List<GroupRecommendBook> contentBaseGroupRecommendBookList = groupRecommendBookRepository.findAllByReGroup(currentMember.getShelfGroup());

        // 현재 인기 리스트 가져오기
        List<GroupRecommendBook> bestRecommendBookList = groupRecommendBookRepository.findAllByReGroup(99);

        RecommendListResponse response = new RecommendListResponse("추천 리스트 조회 성공",
                RecommendListResponseDto.createRecommendListResponseDto(
                        memberRecommendBookList, contentBaseGroupRecommendBookList,
                        ageAndGenderGroupRecommendBookList, bestRecommendBookList));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public int assignGroup(int birthYearString, int gender) {
        // 현재 연도 가져오기
        int currentYear = LocalDate.now().getYear();
        // 나이 계산
        int age = currentYear - birthYearString;

        // 성별: 0은 남성, 1은 여성으로 가정
        if (age < 30) { // 1~20대
            return (gender == 0) ? 5 : 6; // 남성은 그룹 5, 여성은 그룹 6
        } else if (age < 40) { // 30대
            return (gender == 0) ? 7 : 8; // 남성은 그룹 7, 여성은 그룹 8
        } else if (age < 50) { // 40대
            return (gender == 0) ? 9 : 10; // 남성은 그룹 9, 여성은 그룹 10
        } else { // 50대 이상
            return (gender == 0) ? 11 : 12; // 남성은 그룹 11, 여성은 그룹 12
        }
    }
}
