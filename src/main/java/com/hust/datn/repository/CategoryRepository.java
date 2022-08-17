package com.hust.datn.repository;

import com.hust.datn.domain.Author;
import com.hust.datn.domain.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsLeaf(Integer isLeaf);

    @Query(value = "select * from category a \n" +
        "where 1=1 \n" +
        "and ( :q is null or name like %:q% )" +
        "and is_leaf = 1",
        countQuery = "select count(*) from category a \n" +
            "where 1=1 \n" +
            "and ( :q is null or name like %:q%)" +
            "and is_leaf = 1",
        nativeQuery = true
    )
    Page<Category> query(@Param(value = "q") String q,
                       Pageable pageable);
}
