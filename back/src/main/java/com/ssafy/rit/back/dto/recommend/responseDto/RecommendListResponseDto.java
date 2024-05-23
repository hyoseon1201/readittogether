package com.ssafy.rit.back.dto.recommend.responseDto;

import com.ssafy.rit.back.entity.GroupRecommendBook;
import com.ssafy.rit.back.entity.MemberRecommendBook;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RecommendListResponseDto {

    private List<MemberRecommendBookDto> memberRecommendBooks;
    private List<GroupRecommendBookDto> contentBaseGroupRecommendBooks;
    private List<GroupRecommendBookDto> ageAndGenderGroupRecommendBooks;
    private List<GroupRecommendBookDto> bestGroupRecommendBooks;

    public static List<MemberRecommendBookDto> convertToMemberDtoList(List<MemberRecommendBook> memberRecommendBooks) {
        return memberRecommendBooks.stream()
                .map(book -> {
                    String cover = book.getBookId().getCover();
                    String title = book.getBookId().getTitle();
                    int bookId = book.getBookId().getId();
                    return new MemberRecommendBookDto(cover, title, bookId);
                })
                .collect(Collectors.toList());
    }

    public static List<GroupRecommendBookDto> convertToGroupDtoList(List<GroupRecommendBook> groupRecommendBooks) {
        return groupRecommendBooks.stream()
                .map(book -> {
                    String cover = book.getCover();
                    String title = book.getBookId().getTitle();
                    int bookId = book.getBookId().getId();
                    return new GroupRecommendBookDto(cover, title, bookId);
                })
                .collect(Collectors.toList());
    }

    public static RecommendListResponseDto createRecommendListResponseDto(
            List<MemberRecommendBook> memberRecommendBooks,
            List<GroupRecommendBook> contentBaseGroupRecommendBooks,
            List<GroupRecommendBook> ageAndGenderGroupRecommendBooks,
            List<GroupRecommendBook> bestGroupRecommendBooks
    ) {
        return RecommendListResponseDto.builder()
                .memberRecommendBooks(convertToMemberDtoList(memberRecommendBooks))
                .contentBaseGroupRecommendBooks(convertToGroupDtoList(contentBaseGroupRecommendBooks))
                .ageAndGenderGroupRecommendBooks(convertToGroupDtoList(ageAndGenderGroupRecommendBooks))
                .bestGroupRecommendBooks(convertToGroupDtoList(bestGroupRecommendBooks))
                .build();
    }
}
