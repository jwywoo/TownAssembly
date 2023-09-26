package com.example.townassembly.domain.user.follow.entity;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follow")
public class Follow extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="forWhom_id", nullable = false)
    private User forWhom;

    @Column(nullable = false)
    private boolean followStat; // 팔로우 상태를 나타내는 변수
    
    public void setUser(User user) {
        this.user = user;
    }
    public void setForWhom(User forWhom) {
        this.forWhom = forWhom;
    }
    public void setFollowStat(boolean followStat) { this.followStat = followStat; }
}
