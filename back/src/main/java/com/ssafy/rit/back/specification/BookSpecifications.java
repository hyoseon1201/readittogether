package com.ssafy.rit.back.specification;

import com.ssafy.rit.back.entity.Book;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    public static Specification<Book> titleOrAuthorContainsIgnoreCase(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), likePattern);
            Predicate authorPredicate = cb.like(cb.lower(root.get("author")), likePattern);
            return cb.or(titlePredicate, authorPredicate);
        };
    }
}