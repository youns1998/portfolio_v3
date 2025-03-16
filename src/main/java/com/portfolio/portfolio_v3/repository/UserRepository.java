package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ 사용자 이름 기준 조회
    Optional<User> findByUsername(String username);

    // ✅ 이메일 기준 조회
    Optional<User> findByEmail(String email);

    // ✅ 존재 여부 확인 메서드 최적화
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
