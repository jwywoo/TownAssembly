package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import lombok.Getter;

@Getter
public class OpinionResponseDtoList {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionUsername;
    private final Boolean likeStat;
    private final Integer likeCount;


    public OpinionResponseDtoList(Opinion opinion, Boolean likeStat, Integer likeCount) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionUsername = opinion.getUsername();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
    }
}
