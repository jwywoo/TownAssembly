package com.example.townassembly.domain.comment.comment.dto;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String commentTitle;
    private final String commentNickname;
    private final String commentContent;
    private final Boolean likeStat;
    private final Integer likeCount;
    private final UserRoleEnum role;

    public CommentResponseDto(Comment comment,Boolean likeStat,Integer likeCount) {
        this.commentId = comment.getId();
        this.commentTitle = comment.getTitle();
        this.commentNickname = comment.getNickname();
        this.commentContent = comment.getContent();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
        this.role = comment.getRole();
    }
}
