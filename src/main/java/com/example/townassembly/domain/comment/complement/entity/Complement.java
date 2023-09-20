package com.example.townassembly.domain.comment.complement.entity;

import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "complement")
public class Complement extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name ="content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="forWhom_id", nullable = false)
    private User forWhom;

    public Complement(ComplementRequestDto requestDto, User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(ComplementRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setForWhom(User forWhom) {
        this.forWhom = forWhom;
    }
}
