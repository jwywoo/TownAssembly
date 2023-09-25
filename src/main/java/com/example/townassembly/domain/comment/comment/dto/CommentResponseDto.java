package com.example.townassembly.domain.comment.comment.dto;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final String title;
    private final String nickname;
    private final String content;
    private final Boolean likeStat;
    private final Integer likeCount;
    private final UserRoleEnum role;

    public CommentResponseDto(Comment comment,Boolean likeStat,Integer likeCount) {
        this.title = comment.getTitle();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
        this.role = comment.getRole();
    }
}
