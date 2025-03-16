package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board_posts")
@SequenceGenerator(name = "board_seq", sequenceName = "board_seq", allocationSize = 1)
public class BoardPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private boolean isMember;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String boardId;

    @OneToMany(mappedBy = "boardPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(nullable = false)
    private int votes = 0;

    @Column(nullable = false)
    private int views = 0;

    @Column(nullable = false)
    private int commentCount = 0; // ✅ commentCount 필드 추가!

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.ipAddress == null || this.ipAddress.isEmpty()) {
            this.ipAddress = "0.0.0.0";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public synchronized void increaseViews() {
        this.views++;
    }

    public synchronized void adjustVoteCount(boolean upvote) {
        if (upvote) {
            this.votes++;
        } else if (this.votes > 0) {
            this.votes--;
        }
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ 댓글 개수를 자동으로 업데이트하는 메서드 추가
    @PostLoad
    public void updateCommentCount() {
        this.commentCount = (comments != null) ? comments.size() : 0;
    }
}
