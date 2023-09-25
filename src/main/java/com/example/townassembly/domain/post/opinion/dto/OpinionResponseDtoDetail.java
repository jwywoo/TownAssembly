package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

import java.util.List;

@Getter
public class OpinionResponseDtoDetail {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionNickname;
    private final List<CommentResponseDto> commentResponseDtoList;
    private final UserRoleEnum opinionRole;
    // Like Status, Like Count need
    private final Boolean likeStat;
    private final Integer likeCount;

    public OpinionResponseDtoDetail(Opinion opinion, Boolean likeStat, Integer likeCount, List<CommentResponseDto> comments) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionNickname = opinion.getNickname();
        this.commentResponseDtoList = comments;
        this.opinionRole = opinion.getRole();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
    }
}
