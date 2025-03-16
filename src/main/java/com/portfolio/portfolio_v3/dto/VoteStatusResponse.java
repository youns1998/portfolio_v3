package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VoteStatusResponse {

    @JsonProperty("hasVoted")
    private boolean hasVoted;

    @JsonProperty("voteType")
    private String voteType;

    public VoteStatusResponse(boolean hasVoted) {
        this.hasVoted = hasVoted;
        this.voteType = "NONE";
    }

    public static VoteStatusResponse fromEntity(boolean hasVoted, String voteType) {
        return VoteStatusResponse.builder()
                .hasVoted(hasVoted)
                .voteType(voteType != null ? voteType : "NONE")
                .build();
    }
}
