package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.Comment;
import com.portfolio.portfolio_v3.model.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ CommentRepository (댓글 데이터 접근 레이어)
 * - `Comment` 엔티티와 데이터베이스 간의 상호작용을 처리하는 JPA Repository
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * ✅ 특정 게시글에 속한 댓글 목록 조회 (작성일 기준 정렬)
     * - 최신순 정렬로 변경하여 조회 성능 향상
     */
    List<Comment> findByBoardPostOrderByCreatedAtDesc(BoardPost boardPost);
}
