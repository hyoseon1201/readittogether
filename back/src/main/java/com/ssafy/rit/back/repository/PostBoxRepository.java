package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.entity.Postbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostBoxRepository extends JpaRepository<Postbox, Long> {

    @Query("SELECT p FROM Postbox p WHERE p.memberId = :memberId AND p.createdAt >= :startDate AND p.createdAt <= :endDate")
    List<Postbox> findAllByMemberIdAndCreationDateBetween(
            @Param("memberId") Member memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
