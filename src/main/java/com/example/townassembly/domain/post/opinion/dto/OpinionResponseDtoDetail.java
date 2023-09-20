package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpinionResponseDtoDetail {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionUsername;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
    // Like Status, Like Count need

    public OpinionResponseDtoDetail(Opinion opinion) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionUsername = opinion.getUsername();
        this.commentResponseDtoList = opinion.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
