package com.example.townassembly.domain.comment.like.dto;

import lombok.Getter;

@Getter
public class CommentLikeRequestDto {
    private Long userId;
    private Long commentId;
}
