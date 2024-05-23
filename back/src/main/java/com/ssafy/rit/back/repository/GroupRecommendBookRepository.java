package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.GroupRecommendBook;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRecommendBookRepository extends JpaRepository<GroupRecommendBook, Integer> {

    List<GroupRecommendBook> findAllByReGroupOrderByCreatedAt(int reGroup);

    List<GroupRecommendBook> findAllByReGroup(int reGroup);
}
