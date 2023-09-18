package com.example.townassembly.domain.comment.comment.entity;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
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
}
