package com.portfolio.portfolio_v3.service;

import com.portfolio.portfolio_v3.exception.CustomException;
import com.portfolio.portfolio_v3.model.BoardPost;
import com.portfolio.portfolio_v3.model.PostVote;
import com.portfolio.portfolio_v3.model.User;
import com.portfolio.portfolio_v3.repository.BoardPostRepository;
import com.portfolio.portfolio_v3.repository.PostVoteRepository;
import com.portfolio.portfolio_v3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostVoteService {
    private final PostVoteRepository postVoteRepository;
    private final BoardPostRepository boardPostRepository;
    private final UserRepository userRepository;

    @Transactional
    public String vote(Long postId, Long userId, String ipAddress, boolean upvote) {
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        PostVote existingVote = getExistingVote(postId, user, ipAddress);

        if (existingVote != null) {
            if (existingVote.isUpvote() == upvote) {
                postVoteRepository.delete(existingVote);
                post.adjustVoteCount(!upvote);
                return upvote ? "추천 취소" : "비추천 취소";
            } else {
                existingVote.updateVote(upvote); // ✅ 변경된 메서드 사용
                post.adjustVoteCount(upvote);
                return upvote ? "추천 변경" : "비추천 변경";
            }
        }

        postVoteRepository.save(PostVote.builder()
                .post(post)
                .user(user)
                .ipAddress(ipAddress)
                .upvote(upvote)
                .build());

        post.adjustVoteCount(upvote);
        return upvote ? "추천 완료" : "비추천 완료";
    }

    public Boolean hasVoted(Long postId, Long userId, String ipAddress) {
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        return getExistingVote(postId, user, ipAddress) != null;
    }

    private PostVote getExistingVote(Long postId, User user, String ipAddress) {
        return user != null
                ? postVoteRepository.findByPostIdAndUser(postId, user).orElse(null)
                : postVoteRepository.findByPostIdAndIpAddress(postId, ipAddress).orElse(null);
    }
}
