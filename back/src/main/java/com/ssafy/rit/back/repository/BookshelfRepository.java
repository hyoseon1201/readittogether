package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.entity.Bookshelf;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Integer> {

    Optional<Bookshelf> findByBookIdAndMemberId(Book bookId, Member memberId);

    @Query("SELECT COUNT(b) FROM Bookshelf b WHERE b.memberId.id = :memberId AND b.isRate = 1")
    int getRatedBookCnt(@Param("memberId") Long memberId);

    @Query("select count(b) from Bookshelf b where b.memberId.id = :memberId AND b.isRead = 0")
    int getLikedBookCnt(@Param("memberId") Long memberId);

    @Query("select bs.bookId.id from Bookshelf bs where bs.memberId.id = :memberId and bs.isRead = 1 and bs.createdAt >= :startDate and bs.createdAt <= :endDate")
    List<Integer> getBookIdListFromBookshelf(@Param("memberId")Long memberId, @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);


    @Query("SELECT b FROM Bookshelf b WHERE b.memberId.id IN :memberIds")
    List<Bookshelf> findAllByMemberIdIn(@Param("memberIds") List<Long> memberIds);

    List<Bookshelf> findAllByMemberId(Member currentMember);

    // 검색
    @Query("SELECT b FROM Bookshelf b WHERE b.memberId = :memberId " +
            "AND (:search IS NULL OR :search = '' OR b.title LIKE %:search% OR b.bookId.author LIKE %:search%)")
    Page<Bookshelf> findAllByMemberIdAndSearchKeyword(Member memberId, String search, Pageable pageable);

}





