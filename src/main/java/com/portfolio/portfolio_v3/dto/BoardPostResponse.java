package com.portfolio.portfolio_v3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.portfolio_v3.model.BoardPost;
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

    @JsonProperty("userId")
    private final Long userId;

    private final int views;
    private final int votes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    // ✅ 정적 메서드를 통해 엔티티 → DTO 변환
    public static BoardPostResponse fromEntity(BoardPost post) {
        return BoardPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .boardId(post.getBoardId())
                .isMember(post.isMember())
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .views(post.getViews())
                .votes(post.getVotes())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
