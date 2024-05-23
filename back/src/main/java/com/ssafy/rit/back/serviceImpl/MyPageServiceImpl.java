package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.bookGenre.GetReadGenreListDto;
import com.ssafy.rit.back.dto.member.responseDto.FollowMemberResponseDto;
import com.ssafy.rit.back.dto.member.responseDto.MyPageResponseDto;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.repository.BookGenreRepository;
import com.ssafy.rit.back.repository.BookshelfRepository;
import com.ssafy.rit.back.repository.CardRepository;
import com.ssafy.rit.back.repository.GenreRepository;
import com.ssafy.rit.back.service.MyPageService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class MyPageServiceImpl implements MyPageService {

    private final CommonUtil commonUtil;
    private final FollowServiceImpl followService;
    private final BookshelfRepository bookshelfRepository;
    private final CardRepository cardRepository;
    private final BookGenreRepository bookGenreRepository;


    public MyPageServiceImpl(CommonUtil commonUtil, FollowServiceImpl followService, BookshelfRepository bookshelfRepository, CardRepository cardRepository, BookGenreRepository bookGenreRepository) {
        this.commonUtil = commonUtil;
        this.followService = followService;
        this.bookshelfRepository = bookshelfRepository;
        this.cardRepository = cardRepository;
        this.bookGenreRepository = bookGenreRepository;
    }


    @Override
    public int getEvaluatedBookCnt(Long memberId) {
        return bookshelfRepository.getRatedBookCnt(memberId);
    }

    @Override
    public int getLikedBookCnt(Long memberId) {
        return bookshelfRepository.getLikedBookCnt(memberId);
    }

    @Override
    public int getSendCardCnt(Long memberId) {
        return cardRepository.getSendCardCnt(memberId);
    }

    @Override
    public List<Long> getGenreNoList() {
        return null;
    }

    private static final int GENRE_CNT = 21;

    public MyPageResponseDto getMyPage() {

        Member member = commonUtil.getMember();

        log.info("----------------------------------------------------------------------");
        log.info("------------------------{}님의 마이페이지------------------------", member.getNickname());
        log.info("----------------------------------------------------------------------");

        List<Member> followList = followService.getFollowingList(member);
        List<Member> followerList = followService.getFollowerList(member);

        List<FollowMemberResponseDto> followings = makeFollowListResponseDto(followList);
        List<FollowMemberResponseDto> followers = makeFollowListResponseDto(followerList);


        log.info(followList);

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);

        int ratedBookCnt = getEvaluatedBookCnt(member.getId());
        int likedBookCnt = getLikedBookCnt(member.getId());
        int sendCardCnt = getSendCardCnt(member.getId());

        List<Integer> bookIdList = bookshelfRepository.getBookIdListFromBookshelf(member.getId(), startDate, endDate);
        List<Integer> bookGenreIdList = bookGenreRepository.findGenresByBookId(bookIdList);

        List<Integer> readGenreList = calculateGenreCounts(bookGenreIdList, GENRE_CNT);


        MyPageResponseDto responseDto = MyPageResponseDto.builder()
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .followList(followings)
                .followerList(followers)
                .evaluatedBookCnt(ratedBookCnt)
                .likedBookCnt(likedBookCnt)
                .sendCardCnt(sendCardCnt)
                .genreNoList(readGenreList)
                .build();



        return responseDto;
    }


    private List<FollowMemberResponseDto> makeFollowListResponseDto(List<Member> followList) {

        List<FollowMemberResponseDto> list = new ArrayList<>();
        Member currentMember = commonUtil.getMember();

        for (Member f : followList) {
            int isFollowing = 0;
            if (followService.isFollowing(currentMember, f)) {
                isFollowing = 1;
            }
            FollowMemberResponseDto dto = FollowMemberResponseDto.builder()
                    .memberId(f.getId())
                    .profileImage(f.getProfileImage())
                    .nickname(f.getNickname())
                    .isFollowing(isFollowing)
                    .build();

            list.add(dto);
        }

        return list;
    }

    private List<Integer> calculateGenreCounts(List<Integer> bookGenreList, int genres) {
        List<Integer> genreCounts = new ArrayList<>(Collections.nCopies(genres, 0));

        for (Integer genreNumber : bookGenreList) {
            if (genreNumber >= 1 && genreNumber <= genres) {
                int index = genreNumber - 1;
                genreCounts.set(index, genreCounts.get(index) + 1);
            } else {
                System.out.println("----------------유효하지 않은 장르 넘버: " + genreNumber);
            }
        }

        return genreCounts;
    }

}
