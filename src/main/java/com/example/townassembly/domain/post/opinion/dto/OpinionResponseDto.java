package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import lombok.Getter;

@Getter
public class OpinionResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;

    public OpinionResponseDto(Opinion opinion) {
        this.id = opinion.getId();
        this.title = opinion.getTitle();
        this.content = opinion.getContent();
        this.username = opinion.getUsername();
    }
}
