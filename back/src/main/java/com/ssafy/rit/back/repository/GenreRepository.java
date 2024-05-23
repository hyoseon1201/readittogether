package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

//    @Query("")
//    List<Long> getGenreList(@Param("memberId")Long memberId, @Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
