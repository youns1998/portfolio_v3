package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.portfolio_v3.util.ValidationUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardPostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String boardId;

    @JsonProperty("isMember")
    private final boolean isMember;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final String originalIpAddress;

    private final int views;
    private final int votes;

    @JsonProperty("userId")
    private final Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    /**
     * ✅ Lombok의 `isMember()` 자동 생성 방지
     * - `getIsMember()`를 명시적으로 정의하여 JSON 응답에서 `isMember` 필드만 사용하도록 설정
     */
    @JsonProperty("isMember")
    public boolean getIsMember() {
        return isMember;
    }

    /**
     * ✅ IP 주소 마스킹 처리
     */
    @JsonProperty("ipAddress")
    public String getMaskedIp() {
        return ValidationUtil.maskIp(originalIpAddress);
    }

    /**
     * ✅ 엔티티를 DTO로 변환하는 정적 메서드
     */
    public static BoardPostResponse fromEntity(com.portfolio.portfolio_v3.model.BoardPost post) {
        return BoardPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .boardId(post.getBoardId())
                .isMember(post.isMember())
                .userId(post.getUser() != null ? post.getUser().getId() : null) // ✅ User 엔티티에서 ID 가져오기
                .originalIpAddress(post.getIpAddress())
                .views(post.getViews())
                .votes(post.getVotes())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

    }
}
