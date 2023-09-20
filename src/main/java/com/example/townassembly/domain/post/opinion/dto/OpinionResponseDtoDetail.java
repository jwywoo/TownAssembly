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
    private final List<CommentResponseDto> commentResponseDtoList;
    // Like Status, Like Count need
    private final Boolean likeStat;
    private final Integer likeCount;

    public OpinionResponseDtoDetail(Opinion opinion, Boolean likeStat, Integer likeCount, List<CommentResponseDto> comments) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionUsername = opinion.getUsername();
        this.commentResponseDtoList = comments;
        this.likeStat = likeStat;
        this.likeCount = likeCount;
    }
}
