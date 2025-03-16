package com.portfolio.portfolio_v3.controller;

import com.portfolio.portfolio_v3.dto.VoteRequest;
import com.portfolio.portfolio_v3.service.PostVoteService;
import com.portfolio.portfolio_v3.util.ResponseUtil;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostVoteController {
    private final PostVoteService postVoteService;

    /**
     * ✅ 게시글 추천/비추천 처리
     */
    @PostMapping("/{postId}/vote")
    public ResponseEntity<?> votePost(@PathVariable Long postId, @RequestBody VoteRequest voteRequest) {
        return ValidationUtil.isValidVoteRequest(voteRequest)
                ? ResponseUtil.handle(() -> postVoteService.vote(postId, voteRequest.getUserId(), voteRequest.getIpAddress(), voteRequest.isUpvote()))
                : ResponseUtil.error("유효하지 않은 추천 요청입니다.");
    }

    /**
     * ✅ 게시글 추천 여부 조회
     */
    @GetMapping("/{postId}/vote-status")
    public ResponseEntity<?> checkVoteStatus(@PathVariable Long postId,
                                             @RequestParam(required = false) Long userId,
                                             @RequestParam(required = false) String ipAddress) {
        return ResponseUtil.handle(() -> Map.of("hasVoted", postVoteService.hasVoted(postId, userId, ipAddress)));
    }
}
