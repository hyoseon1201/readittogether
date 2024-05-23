package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.GuestBook;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {

    @Query("SELECT gb.id FROM GuestBook gb WHERE gb.toMemberId = :toMemberId ORDER BY gb.id DESC")
    List<Long> findIdsByToMemberId(@Param("toMemberId") Member toMemberId);

}
