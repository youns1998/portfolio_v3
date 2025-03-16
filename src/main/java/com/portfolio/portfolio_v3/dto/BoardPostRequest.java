package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.portfolio_v3.util.ValidationUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPostRequest {
    @NotBlank(message = "제목은 필수 입력 항목입니다")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다")
    private String content;

    @NotBlank(message = "작성자 이름은 필수 입력 항목입니다")
    private String author;

    @JsonProperty("isMember")
    private boolean isMember;

    private String password;

    @JsonProperty("userId")
    private Long userId;

    @NotBlank(message = "게시판 ID는 필수 입력 항목입니다")
    private String boardId;

    @NotNull
    private String ipAddress;

    // ✅ `isValidRequest()` 추가
    public boolean isValidRequest() {
        return ValidationUtil.isNotEmpty(title) &&
               ValidationUtil.isNotEmpty(content) &&
               ValidationUtil.isNotEmpty(author) &&
               ValidationUtil.isNotEmpty(boardId) &&
               ValidationUtil.isValidIp(ipAddress) &&
               (isMember ? ValidationUtil.isValidUserId(userId) : ValidationUtil.isNotEmpty(password));
    }
}
