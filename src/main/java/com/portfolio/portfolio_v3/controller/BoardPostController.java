package com.portfolio.portfolio_v3.controller;

import com.portfolio.portfolio_v3.dto.*;
import com.portfolio.portfolio_v3.service.BoardPostService;
import com.portfolio.portfolio_v3.util.*;
import io.swagger.v3.oas.annotations.*;
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

    @Operation(summary = "게시글 단건 조회", description = "ID로 게시글 조회 및 조회수 증가 옵션")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUtil.ApiResponse<BoardPostResponse>> getPostById(
        @Parameter(description = "게시글 ID") @PathVariable Long id,
        @Parameter(description = "조회수 증가 여부") 
        @RequestParam(defaultValue = "false") boolean increaseViewCount
    ) {
        return ResponseUtil.handle(() -> 
            boardPostService.getPostById(id, increaseViewCount)
        );
    }

    @Operation(summary = "게시글 목록 조회 (게시판 기준)")
    @GetMapping("/board/{boardId}")
    public ResponseEntity<ResponseUtil.ApiResponse<CustomPageResponse<BoardPostResponse>>> getPostsByBoard(
        @Parameter(description = "게시판 ID") @PathVariable String boardId,
        @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "정렬 기준") @RequestParam(defaultValue = "latest") String sortBy
    ) {
        return handlePagedResponse(() -> 
            boardPostService.getPostsByBoard(boardId, page, size, sortBy),
            "게시글 목록 조회 성공"
        );
    }

    @Operation(summary = "게시글 생성", description = "신규 게시글 생성 (비회원시 비밀번호 필수)")
    @PostMapping
    public ResponseEntity<ResponseUtil.ApiResponse<BoardPostResponse>> createPost(
        @Valid @RequestBody BoardPostRequest request
    ) {
        return ResponseUtil.handle(() -> 
            boardPostService.createPost(request)
        );
    }

    // ... (나머지 메서드들도 동일한 패턴으로 수정)

    // === Helper Methods ===
    private ResponseEntity<ResponseUtil.ApiResponse<CustomPageResponse<BoardPostResponse>>> 
        handlePagedResponse(PageSupplier<BoardPostResponse> supplier, String message) {
        
        return ResponseUtil.pagedSuccess(
            new CustomPageResponse<>(supplier.get()),
            message
        );
    }

    // === Functional Interface ===
    @FunctionalInterface
    private interface PageSupplier<T> {
        Page<T> get();
    }
}