package com.portfolio.portfolio_v3.repository;

import com.portfolio.portfolio_v3.model.BoardPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

    // ✅ 최신순으로 게시글 조회
    Page<BoardPost> findByBoardIdOrderByCreatedAtDesc(String boardId, Pageable pageable);

    // ✅ 추천순으로 게시글 조회
    Page<BoardPost> findByBoardIdOrderByVotesDesc(String boardId, Pageable pageable);

    // ✅ 댓글이 많은 게시글 조회 (서브쿼리 활용하여 성능 최적화)
    @Query("""
           SELECT p FROM BoardPost p 
           WHERE p.boardId = :boardId 
           ORDER BY (SELECT COUNT(c) FROM Comment c WHERE c.boardPost = p) DESC
           """)
    Page<BoardPost> findMostCommentedPostsByBoard(@Param("boardId") String boardId, Pageable pageable);

    // ✅ 특정 게시판 내에서 키워드 검색 (제목, 내용, 작성자 포함)
    @Query("""
           SELECT p FROM BoardPost p WHERE p.boardId = :boardId 
           AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')))
           """)
    Page<BoardPost> searchPostsByBoard(@Param("boardId") String boardId, @Param("keyword") String keyword, Pageable pageable);

    // ✅ 전체 게시글 내에서 키워드 검색 (제목, 내용, 작성자 포함)
    @Query("""
           SELECT p FROM BoardPost p 
           WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
    Page<BoardPost> searchAllPosts(@Param("keyword") String keyword, Pageable pageable);
}
