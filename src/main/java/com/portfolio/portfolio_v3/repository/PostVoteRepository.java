package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.PostVote;
import com.portfolio.portfolio_v3.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    // ✅ 특정 회원이 특정 게시글에 투표한 기록 조회
    Optional<PostVote> findFirstByPostIdAndUserId(Long postId, Long userId);

    // ✅ 특정 비회원이 특정 게시글에 대해 투표한 기록 조회 (IP 기반)
    Optional<PostVote> findFirstByPostIdAndIpAddress(Long postId, String ipAddress);

    // ✅ 특정 게시글의 모든 추천/비추천 기록 삭제
    void deleteByPostId(Long postId);
    
    Optional<PostVote> findByPostIdAndUser(Long postId, User user);
    Optional<PostVote> findByPostIdAndIpAddress(Long postId, String ipAddress);

}
