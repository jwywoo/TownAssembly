package com.example.townassembly.domain.comment.comment.entity;

import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.like.entity.CommentLike;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name ="content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opinion_id", nullable = false)
    private Opinion opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, User user) {
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }
}
