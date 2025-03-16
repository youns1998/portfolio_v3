package com.portfolio.portfolio_v3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "user_id"}),
        @UniqueConstraint(columnNames = {"post_id", "ip_address"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // ✅ LAZY 로딩 적용, null 허용 방지
    @JoinColumn(name = "post_id", nullable = false)
    private BoardPost post;

    @Column(nullable = true)
    private Long userId; // ✅ 회원 ID (비회원이면 null)

    @Column(nullable = true, length = 45) // ✅ IP 주소 길이 제한 (IPv6 고려)
    private String ipAddress;

    @Column(nullable = false)
    private boolean upvote;
}
