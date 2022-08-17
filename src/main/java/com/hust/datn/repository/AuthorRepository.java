package com.hust.datn.repository;

import com.hust.datn.domain.Author;

import com.hust.datn.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "select * from author a \n" +
        "where 1=1 \n" +
        "and ( :q is null or name like %:q% )",
        countQuery = "select count(*) from author a \n" +
            "where 1=1 \n" +
            "and ( :q is null or name like %:q% or slug like %:q% )",
        nativeQuery = true
    )
    Page<Author> query(@Param(value = "q") String q,
                        Pageable pageable);
}
