package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✅ ID 기반 equals() & hashCode() 자동 생성
@Entity
@Table(name = "post_votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "post_id", "user_id" }),
        @UniqueConstraint(columnNames = { "post_id", "ip_address" })
})
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // ✅ ID 기반 비교 적용
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // ✅ 게시글과 연관 관계 설정
    @JoinColumn(name = "post_id", nullable = false)
    private BoardPost post;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ 회원이면 `User` 객체를 참조, 비회원은 `null`
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 45) // ✅ 비회원일 경우 IP 주소 저장 (nullable=true 생략 가능)
    private String ipAddress;

    @Column(nullable = false)
    private boolean upvote;

    // ✅ 빌더 패턴을 사용한 생성자
    @Builder
    public PostVote(BoardPost post, User user, String ipAddress, boolean upvote) {
        this.post = post;
        this.user = user;
        this.ipAddress = ipAddress;
        this.upvote = upvote;
    }

    // ✅ 투표 상태 변경 메서드 (중복 저장 방지)
    public void updateVote(boolean newVote) {
        if (this.upvote != newVote) {
            this.upvote = newVote;
        }
    }
}
