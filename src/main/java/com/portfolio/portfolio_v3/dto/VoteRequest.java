package com.portfolio.portfolio_v3.dto;

import com.portfolio.portfolio_v3.util.ValidationUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    private Long userId;      // ✅ 회원 ID (null 가능, 회원 전용)
    private String ipAddress; // ✅ 비회원 IP 주소 (null 가능, 비회원 전용)
    private boolean upvote;   // ✅ true: 추천, false: 비추천

    /**
     * ✅ 추천 요청이 유효한지 확인하는 메서드
     * - 회원이면 `userId`가 필수
     * - 비회원이면 `ipAddress`가 필수
     */
    public boolean isValidVote() {
        return (userId != null && userId > 0) || ValidationUtil.isValidIp(ipAddress);
    }
}
