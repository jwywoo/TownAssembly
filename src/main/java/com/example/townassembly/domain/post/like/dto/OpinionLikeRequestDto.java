package com.example.townassembly.domain.post.like.dto;

import lombok.Getter;

@Getter
public class OpinionLikeRequestDto {
    private Long userId;
    private Long opinionId;
}
