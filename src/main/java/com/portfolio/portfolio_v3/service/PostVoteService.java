package com.portfolio.portfolio_v3.service;

import com.portfolio.portfolio_v3.model.BoardPost;
import com.portfolio.portfolio_v3.model.PostVote;
import com.portfolio.portfolio_v3.repository.BoardPostRepository;
import com.portfolio.portfolio_v3.repository.PostVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostVoteService {
    private final PostVoteRepository postVoteRepository;
    private final BoardPostRepository boardPostRepository;

    @Transactional
    public String vote(Long postId, Long userId, String ipAddress, boolean upvote) {
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        PostVote existingVote = getExistingVote(postId, userId, ipAddress);

        // ✅ 기존에 투표한 경우 -> 상태 변경 또는 취소
        if (existingVote != null) {
            if (existingVote.isUpvote() == upvote) {
                postVoteRepository.delete(existingVote);
                adjustVoteCount(post, !upvote);
                return upvote ? "추천 취소" : "비추천 취소";
            } else {
                existingVote.setUpvote(upvote);
                postVoteRepository.save(existingVote);
                adjustVoteCount(post, upvote);
                return upvote ? "추천 변경" : "비추천 변경";
            }
        }

        // ✅ 새로운 투표
        postVoteRepository.save(PostVote.builder()
                .post(post)
                .userId(userId)
                .ipAddress(ipAddress)
                .upvote(upvote)
                .build());

        adjustVoteCount(post, upvote);
        return upvote ? "추천 완료" : "비추천 완료";
    }

    // ✅ 특정 유저의 추천 상태 확인
    public Boolean hasVoted(Long postId, Long userId, String ipAddress) {
        return getExistingVote(postId, userId, ipAddress) != null;
    }

    // ✅ 기존 투표 기록 조회
    private PostVote getExistingVote(Long postId, Long userId, String ipAddress) {
        if (userId != null) {
            return postVoteRepository.findByPostIdAndUserId(postId, userId).orElse(null);
        } else if (ipAddress != null) {
            return postVoteRepository.findByPostIdAndIpAddress(postId, ipAddress).orElse(null);
        }
        return null;
    }

    // ✅ 추천/비추천 카운트 조정
    private void adjustVoteCount(BoardPost post, boolean upvote) {
        if (upvote) {
            post.upvote();
        } else {
            post.downvote();
        }
        boardPostRepository.save(post);
    }
}
