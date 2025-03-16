package com.portfolio.portfolio_v3.service;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.BoardPostResponse;
import com.portfolio.portfolio_v3.exception.CustomException;
import com.portfolio.portfolio_v3.model.BoardPost;
import com.portfolio.portfolio_v3.repository.BoardPostRepository;
import com.portfolio.portfolio_v3.repository.PostVoteRepository;
import com.portfolio.portfolio_v3.repository.UserRepository;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardPostService {
    private final BoardPostRepository boardPostRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository;

    public BoardPostResponse getPostById(Long id, boolean increaseViewCount) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (increaseViewCount) {
            post.increaseViews();
        }

        return BoardPostResponse.fromEntity(post);
    }

    public Page<BoardPostResponse> getPostsByBoard(String boardId, int page, int size, String sortBy) {
        return boardPostRepository.findByBoardIdOrderByCreatedAtDesc(boardId, getPageable(page, size, sortBy))
                .map(BoardPostResponse::fromEntity);
    }

    public Page<BoardPostResponse> getAllPosts(int page, int size, String sortBy) {
        return boardPostRepository.findAll(getPageable(page, size, sortBy))
                .map(BoardPostResponse::fromEntity);
    }

    @Transactional
    public BoardPostResponse createPost(BoardPostRequest request) {
        ValidationUtil.validateBoardPostRequest(request);

        BoardPost newPost = BoardPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .ipAddress(request.getIpAddress() != null ? request.getIpAddress() : "0.0.0.0")
                .isMember(request.isMember())
                .user(request.isMember() ? userRepository.findById(request.getUserId()).orElse(null) : null)
                .password(request.isMember() ? null : request.getPassword())
                .boardId(request.getBoardId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .votes(0)
                .build();

        boardPostRepository.save(newPost);
        return BoardPostResponse.fromEntity(newPost);
    }

    @Transactional
    public BoardPostResponse updatePost(Long id, BoardPostRequest request) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        post.updatePost(request.getTitle(), request.getContent());
        return BoardPostResponse.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long id) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        postVoteRepository.deleteByPostId(id);
        boardPostRepository.delete(post);
    }

    public Page<BoardPostResponse> searchPosts(String keyword, String boardId, int page, int size, String sortBy) {
        return (ValidationUtil.isNotEmpty(boardId)
                ? boardPostRepository.searchPostsByBoard(boardId, keyword, getPageable(page, size, sortBy))
                : boardPostRepository.searchAllPosts(keyword, getPageable(page, size, sortBy)))
                .map(BoardPostResponse::fromEntity);
    }

    private Pageable getPageable(int page, int size, String sortBy) {
        Sort sort = switch (sortBy) {
            case "popular" -> Sort.by(Sort.Direction.DESC, "votes");
            case "views" -> Sort.by(Sort.Direction.DESC, "views");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        return PageRequest.of(page, size, sort);
    }
}
