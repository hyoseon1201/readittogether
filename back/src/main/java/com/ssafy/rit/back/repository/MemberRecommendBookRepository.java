package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.entity.MemberRecommendBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRecommendBookRepository extends JpaRepository<MemberRecommendBook, Integer> {

    List<MemberRecommendBook> findAllByMemberId(Member memberId);
}
