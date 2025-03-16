package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BoardPostRequest {

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;

    @NotBlank(message = "작성자 이름은 필수 입력 항목입니다.")
    private String author;

    @JsonProperty("isMember")
    private boolean isMember;

    private String password;

    @JsonProperty("userId")
    private Long userId;

    @NotBlank(message = "게시판 ID는 필수 입력 항목입니다.")
    private String boardId;

    @NotNull(message = "IP 주소는 필수 입력 항목입니다.")
    private String ipAddress;
}
