package com.example.townassembly.domain.comment.comment.dto;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final String title;
    private final String username;
    private final String content;
    private final Boolean likeStat;
    private final Integer likeCount;

    public CommentResponseDto(Comment comment,Boolean likeStat,Integer likeCount) {
        this.title = comment.getTitle();
        this.username = comment.getUsername();
        this.content = comment.getContent();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
    }
}
