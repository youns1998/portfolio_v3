package com.portfolio.portfolio_v3.service;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.BoardPostResponse;
import com.portfolio.portfolio_v3.model.BoardPost;
import com.portfolio.portfolio_v3.repository.BoardPostRepository;
import com.portfolio.portfolio_v3.repository.PostVoteRepository;
import com.portfolio.portfolio_v3.repository.UserRepository; // ✅ 추가
import com.portfolio.portfolio_v3.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardPostService {
    private final BoardPostRepository boardPostRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository; // ✅ 추가

    public BoardPostResponse getPostById(Long id, boolean increaseViewCount) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (increaseViewCount) {
            post.increaseViews();
            boardPostRepository.save(post);
        }
        return convertToResponse(post);
    }

    public Page<BoardPostResponse> getPostsByBoard(String boardId, int page, int size, String sortBy) {
        return boardPostRepository.findLatestPostsByBoard(boardId, getPageable(page, size, sortBy))
                .map(this::convertToResponse);
    }

    public Page<BoardPostResponse> getAllPosts(int page, int size, String sortBy) {
        return boardPostRepository.findAll(getPageable(page, size, sortBy))
                .map(this::convertToResponse);
    }

    public BoardPostResponse createPost(BoardPostRequest request) {
        ValidationUtil.validateBoardPostRequest(request); // ✅ 유효성 검사 일괄 처리

        BoardPost newPost = BoardPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .ipAddress(request.getIpAddress() != null ? request.getIpAddress() : "0.0.0.0")
                .isMember(request.isMember())
                .user(request.isMember() ? userRepository.findById(request.getUserId()).orElse(null) : null) // ✅ User 엔티티 매핑
                .password(request.isMember() ? null : request.getPassword()) // ✅ 비회원만 비밀번호 저장
                .boardId(request.getBoardId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .votes(0)
                .build();

        return convertToResponse(boardPostRepository.save(newPost));
    }

    public BoardPostResponse updatePost(Long id, BoardPostRequest request) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return convertToResponse(boardPostRepository.save(post));
    }
//커밋 테스트
    @Transactional
    public void deletePost(Long id, String password) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        postVoteRepository.deleteByPostId(id);
        boardPostRepository.delete(post);
    }

    public Page<BoardPostResponse> searchPosts(String keyword, String boardId, int page, int size, String sortBy) {
        return (ValidationUtil.isNotEmpty(boardId)
                ? boardPostRepository.searchPostsByBoard(boardId, keyword, getPageable(page, size, sortBy))
                : boardPostRepository.searchAllPosts(keyword, getPageable(page, size, sortBy)))
                .map(this::convertToResponse);
    }

    private Pageable getPageable(int page, int size, String sortBy) {
        Sort sort = switch (sortBy) {
            case "popular" -> Sort.by(Sort.Direction.DESC, "votes");
            case "views" -> Sort.by(Sort.Direction.DESC, "views");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        return PageRequest.of(page, size, sort);
    }

    private BoardPostResponse convertToResponse(BoardPost post) {
        return BoardPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .boardId(post.getBoardId())
                .isMember(post.isMember())
                .userId(post.getUser() != null ? post.getUser().getId() : null) // ✅ 수정됨
                .originalIpAddress(post.getIpAddress())
                .views(post.getViews())
                .votes(post.getVotes())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
