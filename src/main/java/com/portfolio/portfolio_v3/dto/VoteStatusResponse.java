package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteStatusResponse {
    @JsonProperty("hasVoted")
    private final boolean hasVoted;
    
    @JsonProperty("voteType")
    private final String voteType; // "UP", "DOWN", "NONE"

    // 정적 팩토리 메서드
    public static VoteStatusResponse fromEntity(boolean hasVoted, String voteType) {
        return VoteStatusResponse.builder()
                .hasVoted(hasVoted)
                .voteType(voteType != null ? voteType : "NONE")
                .build();
    }

    // 확장성을 고려한 생성자
    public VoteStatusResponse(boolean hasVoted, String voteType) {
        this.hasVoted = hasVoted;
        this.voteType = voteType != null ? voteType : "NONE";
    }
}