package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    /**
     * ✅ 특정 회원이 특정 게시글에 대해 투표한 기록 조회
     * - 존재할 가능성이 높은 데이터를 조회할 때는 `existsBy`를 활용하여 성능 향상
     */
    Optional<PostVote> findByPostIdAndUserId(Long postId, Long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    /**
     * ✅ 특정 비회원이 특정 게시글에 대해 투표한 기록 조회 (IP 기반)
     */
    Optional<PostVote> findByPostIdAndIpAddress(Long postId, String ipAddress);
    boolean existsByPostIdAndIpAddress(Long postId, String ipAddress);

    /**
     * ✅ 특정 게시글의 모든 추천/비추천 기록 삭제
     */
    void deleteByPostId(Long postId);
}
