package com.example.townassembly.domain.comment.comment.dto;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final String title;
    private final String username;
    private final String content;

    public CommentResponseDto(Comment comment) {
        this.title = comment.getTitle();
        this.username = comment.getUsername();
        this.content = comment.getContent();
    }
}
