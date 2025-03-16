package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteStatusResponse {
    @JsonProperty("hasVoted")
    private boolean hasVoted;

    @JsonProperty("voteType")
    private String voteType; // "UP", "DOWN", "NONE"

    // ✅ boolean만 받는 생성자 추가
    public VoteStatusResponse(boolean hasVoted) {
        this.hasVoted = hasVoted;
        this.voteType = "NONE"; // 기본값 설정
    }

    // 정적 팩토리 메서드 (유지)
    public static VoteStatusResponse fromEntity(boolean hasVoted, String voteType) {
        return VoteStatusResponse.builder()
                .hasVoted(hasVoted)
                .voteType(voteType != null ? voteType : "NONE")
                .build();
    }
}
