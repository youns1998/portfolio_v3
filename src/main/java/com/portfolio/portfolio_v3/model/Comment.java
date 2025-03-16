package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ✅ Comment (댓글 엔티티)
 * - 게시글에 달린 댓글 정보를 저장하는 엔티티
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
public class Comment {

    /** ✅ 댓글 ID (자동 증가) */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    private Long id;

    /** ✅ 댓글 내용 (대용량 지원) */
    @Column(nullable = false, columnDefinition = "CLOB")
    private String content;

    /** ✅ 댓글 작성자 */
    @Column(nullable = false)
    private String author;

    /** ✅ 댓글 작성자의 IP 주소 */
    @Column(nullable = false)
    private String ipAddress;

    /** ✅ 회원 여부 (true: 회원, false: 비회원) */
    @Column(nullable = false)
    private boolean isMember;

    /** ✅ 비회원의 경우 비밀번호 (회원일 경우 null) */
    private String password;

    /** ✅ 댓글 생성 날짜 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** ✅ 댓글 수정 날짜 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** ✅ 댓글이 속한 게시글 (N:1 관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private BoardPost boardPost;

    /** ✅ 작성자 (회원일 경우 연결, 비회원은 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** ✅ 댓글이 처음 생성될 때 현재 시간 설정 */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /** ✅ 댓글이 수정될 때 updatedAt을 업데이트 */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
