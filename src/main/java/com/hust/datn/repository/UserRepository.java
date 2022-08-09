package com.hust.datn.repository;

import com.hust.datn.domain.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneById(Long userId);

    List<User> findByIdIn(List<Long> userIdList);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    @Query(value = "select a.* from jhi_user a \n" +
        "where 1=1 \n" +
        "and (:authorities is null or a.id in (select user_id from jhi_user_authority b where b.authority_name = :authorities))\n" +
        "and (:keyword is null or lower(a.email) like %:keyword% ESCAPE '&' or lower(a.login) like %:keyword% ESCAPE '&')",
    countQuery = "select count(*) from jhi_user a \n" +
        "where 1=1 \n" +
        "and (:authorities is null or a.id in (select user_id from jhi_user_authority b where b.authority_name = :authorities))\n" +
        "and (:keyword is null or lower(a.email) like %:keyword% ESCAPE '&' or lower(a.login) like %:keyword% ESCAPE '&')",
    nativeQuery = true)
    Page<User> queryUser(@Param("authorities") String authorities, @Param("keyword") String keyword, Pageable pageable);
}
