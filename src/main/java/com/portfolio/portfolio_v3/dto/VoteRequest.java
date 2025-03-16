package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequest {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("isUpvote")
    private boolean upvote;

    @AssertTrue(message = "유효하지 않은 투표 요청입니다.")
    public boolean isValidRequest() {
        return (userId != null && userId > 0) ^ (ipAddress != null && !ipAddress.isEmpty());
    }
}
