package com.example.townassembly.domain.post.opinion.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpinionResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    public OpinionResponseDto(Opinion opinion) {
        this.id = opinion.getId();
        this.title = opinion.getTitle();
        this.content = opinion.getContent();
        this.username = opinion.getUsername();
        this.commentResponseDtoList = opinion.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
