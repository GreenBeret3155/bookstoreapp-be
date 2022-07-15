package com.hust.datn.repository;

import com.hust.datn.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product p \n" +
        "where 1=1 \n" +
        "and ( :author_id is null or author_id = :author_id )\n" +
        "and ( :category_id is null or category_id = :category_id ) \n" +
        "and ( :q is null or name like %:q% )" +
        "and ( :status is null or status = :status)",
        countQuery = "select count(*) from product p \n" +
        "where 1=1 \n" +
        "and ( :author_id is null or author_id = :author_id )\n" +
        "and ( :category_id is null or category_id = :category_id ) \n" +
        "and ( :q is null or name like %:q% )",
        nativeQuery = true
    )
    Page<Product> query(@Param(value = "author_id") Long authorId,
                        @Param(value = "category_id") Long categoryId,
                        @Param(value = "q") String q,
                        @Param(value = "status") Integer status,
                        Pageable pageable);
}
