package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ✅ User (사용자 엔티티)
 * - 회원 정보를 저장하는 엔티티
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class User {

    /** ✅ 사용자 ID (자동 증가) */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    /** ✅ 사용자명 (고유 값) */
    @Column(nullable = false, unique = true, length = 50) // ✅ 길이 제한 추가
    private String username;

    /** ✅ 이메일 (고유 값) */
    @Column(nullable = false, unique = true, length = 100) // ✅ 길이 제한 추가
    private String email;

    /** ✅ 비밀번호 (암호화 저장 필요) */
    @Column(nullable = false, length = 255) // ✅ 보안상 충분한 길이 설정
    private String password;

    /** ✅ 계정 생성 시간 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** ✅ 계정 정보 수정 시간 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** ✅ 사용자가 작성한 게시글 (1:N 관계) */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardPost> posts;

    /** ✅ 사용자가 작성한 댓글 (1:N 관계) */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    /** ✅ 계정 생성 시 자동으로 `createdAt`, `updatedAt` 설정 */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /** ✅ 계정 정보 수정 시 `updatedAt` 업데이트 */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
