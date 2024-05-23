package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.entity.Book;
import com.ssafy.rit.back.entity.Comment;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBookId(Book book, Pageable pageable);

    Optional<Comment> findByBookIdAndMemberId(Book bookId, Member memberId);
}
