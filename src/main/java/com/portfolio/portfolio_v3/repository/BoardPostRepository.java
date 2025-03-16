package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.BoardPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ✅ BoardPostRepository (게시글 데이터 접근 레이어)
 * - `BoardPost` 엔티티와 데이터베이스 간의 상호작용을 처리하는 JPA Repository
 */
@Repository
public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

    /**
     * ✅ 최신순으로 게시글 조회
     */
    Page<BoardPost> findByBoardIdOrderByCreatedAtDesc(String boardId, Pageable pageable);

    /**
     * ✅ 추천순으로 게시글 조회
     */
    Page<BoardPost> findByBoardIdOrderByVotesDesc(String boardId, Pageable pageable);

    /**
     * ✅ 댓글 많은 순으로 게시글 조회
     * - `JOIN FETCH`를 사용하여 성능 최적화
     */
    @Query("SELECT p FROM BoardPost p LEFT JOIN p.comments c WHERE p.boardId = :boardId GROUP BY p ORDER BY COUNT(c) DESC")
    Page<BoardPost> findMostCommentedPostsByBoard(@Param("boardId") String boardId, Pageable pageable);

    /**
     * ✅ 전체 게시글 검색 (제목, 내용, 작성자 포함)
     */
    @Query("SELECT p FROM BoardPost p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<BoardPost> searchAllPosts(@Param("keyword") String keyword, Pageable pageable);

    /**
     * ✅ 특정 게시판에서 검색
     */
    @Query("SELECT p FROM BoardPost p WHERE p.boardId = :boardId AND (" +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BoardPost> searchPostsByBoard(@Param("boardId") String boardId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM BoardPost p WHERE p.boardId = :boardId ORDER BY p.createdAt DESC")
    Page<BoardPost> findLatestPostsByBoard(@Param("boardId") String boardId, Pageable pageable);

}
