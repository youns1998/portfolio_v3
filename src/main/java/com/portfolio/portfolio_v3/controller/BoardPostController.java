package com.portfolio.portfolio_v3.controller;

import com.portfolio.portfolio_v3.dto.*;
import com.portfolio.portfolio_v3.service.BoardPostService;
import com.portfolio.portfolio_v3.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글 관리 API", description = "게시글 CRUD 및 검색 기능 제공")
public class BoardPostController {

    private final BoardPostService boardPostService;

    @Operation(summary = "게시글 조회", description = "게시글 ID로 조회, 조회수 증가 가능")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUtil.ApiResponse<BoardPostResponse>> getPostById(
        @Parameter(description = "게시글 ID") @PathVariable Long id,
        @RequestParam(defaultValue = "false") boolean increaseViewCount
    ) {
        return ResponseUtil.handle(() -> boardPostService.getPostById(id, increaseViewCount));
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping("/board/{boardId}")
    public ResponseEntity<ResponseUtil.ApiResponse<CustomPageResponse<BoardPostResponse>>> getPostsByBoard(
        @Parameter(description = "게시판 ID") @PathVariable String boardId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "latest") String sortBy
    ) {
        Page<BoardPostResponse> result = boardPostService.getPostsByBoard(boardId, page, size, sortBy);
        return ResponseUtil.success(new CustomPageResponse<>(result), "게시글 목록 조회 성공");
    }

    @Operation(summary = "게시글 생성", description = "회원/비회원 글쓰기 지원")
    @PostMapping
    public ResponseEntity<ResponseUtil.ApiResponse<BoardPostResponse>> createPost(
        @Valid @RequestBody BoardPostRequest request
    ) {
        return ResponseUtil.handle(() -> boardPostService.createPost(request));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUtil.ApiResponse<BoardPostResponse>> updatePost(
        @PathVariable Long id,
        @Valid @RequestBody BoardPostRequest request
    ) {
        return ResponseUtil.handle(() -> boardPostService.updatePost(id, request));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtil.ApiResponse<Void>> deletePost(
        @PathVariable Long id
    ) {
        return ResponseUtil.handle(() -> {
            boardPostService.deletePost(id);
            return null;
        });
    }

    @Operation(summary = "게시글 검색")
    @GetMapping("/search")
    public ResponseEntity<ResponseUtil.ApiResponse<CustomPageResponse<BoardPostResponse>>> searchPosts(
        @RequestParam String keyword,
        @RequestParam(required = false) String boardId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "latest") String sortBy
    ) {
        Page<BoardPostResponse> result = boardPostService.searchPosts(keyword, boardId, page, size, sortBy);
        return ResponseUtil.success(new CustomPageResponse<>(result), "게시글 검색 결과");
    }
}
