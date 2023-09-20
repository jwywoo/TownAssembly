package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpinionResponseDto {
    private final Long opinionId;
    private final String opinionTitle;
    private final String opinionContent;
    private final String opinionUsername;


    public OpinionResponseDto(Opinion opinion) {
        this.opinionId = opinion.getId();
        this.opinionTitle = opinion.getTitle();
        this.opinionContent = opinion.getContent();
        this.opinionUsername = opinion.getUsername();
    }
}
