package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("isUpvote")
    private boolean upvote;

    @AssertTrue(message = "유효하지 않은 투표 요청입니다")
    public boolean isValidRequest() {
        return (userId != null && userId > 0) ^ ValidationUtil.isValidIp(ipAddress);
    }
}