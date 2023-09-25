package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class OpinionResponseDtoList {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionNickname;
    private final UserRoleEnum opinionRole;
    private final Boolean likeStat;
    private final Integer likeCount;


    public OpinionResponseDtoList(Opinion opinion, Boolean likeStat, Integer likeCount) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionNickname = opinion.getNickname();
        this.opinionRole = opinion.getRole();
        this.likeStat = likeStat;
        this.likeCount = likeCount;
    }
}
