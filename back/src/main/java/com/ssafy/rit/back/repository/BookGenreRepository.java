package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.dto.bookGenre.GetReadGenreListDto;
import com.ssafy.rit.back.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookGenreRepository extends JpaRepository<BookGenre, Integer> {
    List<BookGenre> findByBookId_Id(int bookId);


    @Query("select bg.genreId.id from BookGenre bg where bg.bookId.id IN :bookIdList")
    List<Integer> findGenresByBookId(@Param("bookIdList")List<Integer> bookIdList);


}


