package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.Comment;
import com.portfolio.portfolio_v3.model.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ✅ 특정 게시글의 댓글 최신순 조회
    List<Comment> findByBoardPostOrderByCreatedAtDesc(BoardPost boardPost);

    // ✅ 특정 게시글의 댓글 개수 조회 (성능 최적화)
    long countByBoardPost(BoardPost boardPost);
}
