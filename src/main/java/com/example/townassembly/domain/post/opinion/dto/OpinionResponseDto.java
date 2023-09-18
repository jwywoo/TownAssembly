package com.example.townassembly.domain.post.opinion.dto;

import lombok.Getter;

@Getter
public class OpinionResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
}
