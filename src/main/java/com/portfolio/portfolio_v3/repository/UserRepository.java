package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ✅ UserRepository (사용자 데이터 접근 레이어)
 * - `User` 엔티티와 데이터베이스 간의 상호작용을 처리하는 JPA Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ✅ 사용자 이름(`username`)을 기준으로 사용자 조회
     */
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username); // ✅ 존재 여부 확인 (성능 최적화)

    /**
     * ✅ 이메일(`email`)을 기준으로 사용자 조회
     */
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // ✅ 존재 여부 확인 (성능 최적화)
}
