package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class OpinionResponseDto {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionNickname;
    private final UserRoleEnum opinionRole;
    private final String opinionImageUrl;


    public OpinionResponseDto(Opinion opinion) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionNickname = opinion.getNickname();
        this.opinionRole = opinion.getRole();
        this.opinionImageUrl = opinion.getImageUrl();
    }
}
