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

    private boolean followStat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="forWhom_id", nullable = false)
    private User forWhom;
    public void setUser(User user) {
        this.user = user;
    }
    public void setForWhom(User forWhom) {
        this.forWhom = forWhom;
    }

    public void setFollowStat(User user) {
        this.followStat = followStat;
    }
}
