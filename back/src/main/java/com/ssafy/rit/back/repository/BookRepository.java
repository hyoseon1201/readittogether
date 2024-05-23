package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);

    @Query("SELECT b from Book b WHERE b.id IN :bookIds")
    List<Book> findAllByBookIds(@Param("bookIds") List<Integer> bookIds);

    @Query("SELECT COALESCE(AVG(c.rating), 0) FROM Comment c WHERE c.bookId.id = :bookId")
    Integer findAverageRatingByBookId(@Param("bookId") Integer bookId);


    @Query("select count(*) from Comment c where c.bookId.id = :bookId")
    Integer findCountCommentByBookId(@Param("bookId") Integer bookId);

    @Query(value = "SELECT * FROM book WHERE title LIKE %:query% OR author LIKE %:query% ORDER BY STR_TO_DATE(pub_date, '%Y년 %m월 %d일') DESC", nativeQuery = true)
    Page<Book> findByQueryAndSortByPubDate(@Param("query") String query, Pageable pageable);

}
