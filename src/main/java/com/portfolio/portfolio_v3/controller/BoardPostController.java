package com.portfolio.portfolio_v3.controller;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.BoardPostResponse;
import com.portfolio.portfolio_v3.dto.CustomPageResponse;
import com.portfolio.portfolio_v3.service.BoardPostService;
import com.portfolio.portfolio_v3.util.ResponseUtil;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardPostController {
    private final BoardPostService boardPostService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id,
                                         @RequestParam(defaultValue = "false") boolean increaseViewCount) {
        return ResponseUtil.handle(() -> boardPostService.getPostById(id, increaseViewCount));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getPostsByBoard(@PathVariable String boardId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "latest") String sortBy) {
        return ResponseUtil.handle(() -> new CustomPageResponse<>(boardPostService.getPostsByBoard(boardId, page, size, sortBy)));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "latest") String sortBy) {
        return ResponseUtil.handle(() -> new CustomPageResponse<>(boardPostService.getAllPosts(page, size, sortBy)));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody BoardPostRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return errorResponse(result.getFieldErrors());
        }

        if (!request.isMember() && ValidationUtil.isInvalidPassword(request.getPassword())) {
            return ResponseUtil.error("비회원은 비밀번호를 필수로 입력해야 합니다.");
        }

        return ResponseUtil.handle(() -> boardPostService.createPost(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody BoardPostRequest request) {
        return ResponseUtil.handle(() -> boardPostService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @RequestParam(required = false) String password) {
        return ResponseUtil.handle(() -> {
            boardPostService.deletePost(id, password);
            return "게시글이 삭제되었습니다.";
        });
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String boardId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "latest") String sortBy) {
        return ResponseUtil.handle(() -> new CustomPageResponse<>(boardPostService.searchPosts(keyword, boardId, page, size, sortBy)));
    }

    private ResponseEntity<?> errorResponse(List<FieldError> errors) {
        String errorMessage = errors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", ")); // ✅ 리스트를 문자열로 변환

        return ResponseUtil.error(errorMessage);
    }
}
