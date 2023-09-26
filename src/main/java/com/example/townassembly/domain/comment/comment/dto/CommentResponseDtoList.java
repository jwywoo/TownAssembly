package com.example.townassembly.domain.comment.comment.dto;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class CommentResponseDtoList {
    private final Long commentId;
    private final String commentTitle;
    private final String commentNickname;
    private final String commentContent;
    private final UserRoleEnum role;

    public CommentResponseDtoList(Comment comment) {
        this.commentId = comment.getId();
        this.commentTitle = comment.getTitle();
        this.commentNickname = comment.getNickname();
        this.commentContent = comment.getContent();
        this.role = comment.getRole();
    }
}
