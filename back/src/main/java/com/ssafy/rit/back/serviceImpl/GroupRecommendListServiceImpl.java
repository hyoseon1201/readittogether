package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.entity.Bookshelf;
import com.ssafy.rit.back.entity.GroupRecommendBook;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.repository.BookRepository;
import com.ssafy.rit.back.repository.BookshelfRepository;
import com.ssafy.rit.back.repository.GroupRecommendBookRepository;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.service.GroupRecommendListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupRecommendListServiceImpl implements GroupRecommendListService {

    private final GroupRecommendBookRepository groupRecommendBookRepository;
    private final MemberRepository memberRepository;
    private final BookshelfRepository bookshelfRepository;
    private final BookRepository bookRepository;
    private final static int contentGroupNum = 5;
    private final static int ageAndGenderGroupNum = 13;

    @Override
    @Transactional
    public void createGroupRecommendList() {

        // 그룹별 추천 리스트 갱신
        for (int groupNum = 0; groupNum < contentGroupNum; groupNum++) {
            List<GroupRecommendBook> contentBaseGroupRecList = groupRecommendBookRepository.findAllByReGroupOrderByCreatedAt(groupNum);
            List<Member> contentBaseShelfGroup = memberRepository.findAllByShelfGroup(groupNum);
            changeRecommendList(contentBaseShelfGroup, contentBaseGroupRecList);
        }

        for (int groupNum = contentGroupNum; groupNum < ageAndGenderGroupNum; groupNum++) {
            int currentYear = LocalDate.now().getYear();
            int startYear, endYear, gender;
            if (groupNum == 5 || groupNum == 6) {
                endYear = currentYear; // 0살부터
                startYear = currentYear - 29; // 29살까지
            } else if (groupNum == 7 || groupNum == 8) {
                endYear = currentYear - 30;
                startYear = currentYear - 39;
            } else if (groupNum == 9 || groupNum == 10) {
                endYear = currentYear - 40;
                startYear = currentYear - 49;
            } else { // 50세 이상
                endYear = currentYear - 50;
                startYear = currentYear - 150;
            }
            gender = groupNum % 2 == 0 ? 1 : 0;

            List<GroupRecommendBook> ageAndGenderGroupRecList = groupRecommendBookRepository.findAllByReGroupOrderByCreatedAt(groupNum);
            List<Member> ageAndGenderShelfGroup = memberRepository.findByBirthBetweenAndGender(startYear, endYear, gender);
            changeRecommendList(ageAndGenderShelfGroup, ageAndGenderGroupRecList);
        }
        List<GroupRecommendBook> bestGroupRecList = groupRecommendBookRepository.findAllByReGroupOrderByCreatedAt(99);
        List<Member> all = memberRepository.findAll();
        changeRecommendList(all, bestGroupRecList);
    }

    @Override
    @Transactional
    public void changeIsReceivable() {
        List<Member> allMember = memberRepository.findAll();
        for (Member member : allMember) {
            member.setIsReceivable(1);
        }
    }

    @Override
    @Transactional
    public void setTempBookCover() {
        List<GroupRecommendBook> allBooks = groupRecommendBookRepository.findAll();
        for (GroupRecommendBook book : allBooks) {
            Book currentBook = book.getBookId();
            String cover = currentBook.getCover();
            book.setCover(cover);
        }
    }

    private void changeRecommendList(List<Member> allByShelfGroup, List<GroupRecommendBook> allByRecList) {
        List<Long> memberIds = allByShelfGroup.stream().map(Member::getId).collect(Collectors.toList());
        List<Bookshelf> booksReadByGroup = bookshelfRepository.findAllByMemberIdIn(memberIds);

        // 책별로 읽힌 횟수를 계산합니다. 여기서는 Bookshelf 인스턴스에서 Book ID를 추출해야 합니다.
        Map<Integer, Long> bookFrequency = booksReadByGroup.stream()
                .collect(Collectors.groupingBy(bookshelf -> bookshelf.getBookId().getId(), Collectors.counting()));

        // 가장 많이 읽힌 책 상위 10개 id
        List<Integer> top10BookIds = bookFrequency.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Book> top10Books = bookRepository.findAllById(top10BookIds);

        for (int thisRecommendBook = 0; thisRecommendBook < top10Books.size(); thisRecommendBook++) {
            GroupRecommendBook recommendBook = allByRecList.get(thisRecommendBook);
            Book book = top10Books.get(thisRecommendBook);
            recommendBook.setBookId(book);
            recommendBook.setCover(book.getCover());
            recommendBook.setCreatedAt(LocalDate.now());
        }
    }
}