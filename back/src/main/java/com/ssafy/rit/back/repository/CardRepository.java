package com.ssafy.rit.back.repository;
import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.rit.back.entity.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface CardRepository extends JpaRepository<Card,Long> {
    //생성

    // 우편함에서 카드 3개 가져와야 하는데, 아직 추천시스템 완성 안돼서 랜덤으로 가져오는거 넣어두겠습니다.
    @Query(value = "SELECT * FROM card WHERE to_member_id != :excludedMember ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Card> findRandomCardsExcludingMember(@Param("excludedMember") Long excludedMemberId);

    @Query("SELECT COUNT(c) FROM Card c WHERE c.fromMemberId.id = :fromMemberId")
    int getSendCardCnt(@Param("fromMemberId")Long fromMemberId);

    Page<Card> findByFromMemberIdAndDeletedBySenderIsFalse(Member fromMember, Pageable pageable);
    Page<Card> findByToMemberIdAndDeletedByRecipientIsFalse(Member toMember, Pageable pageable);

    @Query("SELECT c FROM Card c WHERE c.fromMemberId IN :members AND c.toMemberId != :excludedMember AND c.fromMemberId != :excludedMember")
    List<Card> findByFromMemberIdInAndToMemberIdNot(@Param("members") List<Member> members, @Param("excludedMember") Member excludedMember);

    @Query("SELECT c FROM Card c WHERE c.createdAt BETWEEN :startDate AND :endDate AND c.toMemberId != :excludedMember AND c.fromMemberId != :excludedMember")
    List<Card> findCardsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Member excludedMember);

    @Query("SELECT c FROM Card c WHERE c.bookId IN :books AND c.toMemberId != :excludedMember AND c.fromMemberId != :excludedMember")
    List<Card> findByBooksAndExcludedMember(@Param("books") List<Book> books, @Param("excludedMember") Member excludedMember);
}
