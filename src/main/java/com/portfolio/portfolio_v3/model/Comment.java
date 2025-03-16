package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ✅ 기본 생성자 보호
@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    private Long id;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private boolean isMember;

    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ Lazy 로딩 적용
    @JoinColumn(name = "post_id", nullable = false)
    private BoardPost boardPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, String author, String ipAddress, boolean isMember, String password, BoardPost boardPost, User user) {
        this.content = content;
        this.author = author;
        this.ipAddress = ipAddress;
        this.isMember = isMember;
        this.password = password;
        this.boardPost = boardPost;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateComment(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
