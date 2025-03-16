package com.portfolio.portfolio_v3.controller;

import com.portfolio.portfolio_v3.dto.VoteRequest;
import com.portfolio.portfolio_v3.dto.VoteStatusResponse;
import com.portfolio.portfolio_v3.exception.CustomException;
import com.portfolio.portfolio_v3.service.PostVoteService;
import com.portfolio.portfolio_v3.util.ResponseUtil;
import com.portfolio.portfolio_v3.util.ValidationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostVoteController {

    private final PostVoteService postVoteService;

    @Operation(summary = "게시글 추천/비추천", description = "사용자 ID 또는 IP 기반 추천 처리")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "추천 성공"),
        @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
        @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @PostMapping("/{postId}/vote")
    public ResponseEntity<ResponseUtil.ApiResponse<String>> votePost(
        @Parameter(description = "게시글 ID") @PathVariable Long postId,
        @Valid @RequestBody VoteRequest voteRequest
    ) {
        if (!ValidationUtil.isValidVoteRequest(voteRequest)) {
            throw new CustomException("유효하지 않은 추천 요청", HttpStatus.BAD_REQUEST);
        }

        postVoteService.vote(postId, voteRequest.getUserId(), voteRequest.getIpAddress(), voteRequest.isUpvote());
        return ResponseUtil.success("추천 처리 완료", "추천이 정상적으로 처리되었습니다.");
    }

    @Operation(summary = "추천 여부 조회", description = "사용자의 추천 여부 확인")
    @GetMapping("/{postId}/vote-status")
    public ResponseEntity<ResponseUtil.ApiResponse<VoteStatusResponse>> checkVoteStatus(
        @Parameter(description = "게시글 ID") @PathVariable Long postId,
        @Parameter(description = "사용자 ID") @RequestParam(required = false) Long userId,
        @Parameter(description = "IP 주소") @RequestParam(required = false) String ipAddress
    ) {
        return ResponseUtil.handle(() -> {
            boolean hasVoted = postVoteService.hasVoted(postId, userId, ipAddress);
            return new VoteStatusResponse(hasVoted);
        });
    }
}