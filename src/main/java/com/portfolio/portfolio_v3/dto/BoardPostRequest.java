package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPostRequest {

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;

    @NotBlank(message = "작성자 이름은 필수 입력 항목입니다.")
    private String author;

    private String ipAddress;

    @JsonProperty("isMember")
    private boolean isMember;

    private String password;
    private Long userId;

    @NotBlank(message = "게시판 ID는 필수 입력 항목입니다.")
    private String boardId;

    /**
     * ✅ 필수 항목 검증 (비회원: 비밀번호 필요, 회원: userId 필요)
     */
    public boolean isValidRequest() {
        return ValidationUtil.isNotEmpty(title) &&
                ValidationUtil.isNotEmpty(content) &&
                ValidationUtil.isNotEmpty(author) &&
                ValidationUtil.isNotEmpty(boardId) &&
                (isMember ? ValidationUtil.isValidUserId(userId) : !ValidationUtil.isInvalidPassword(password));
    }
}
