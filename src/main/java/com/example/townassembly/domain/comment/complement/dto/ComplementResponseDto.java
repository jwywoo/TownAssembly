package com.example.townassembly.domain.comment.complement.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import lombok.Getter;

@Getter
public class ComplementResponseDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String content;

    public ComplementResponseDto(Complement complement) {
        this.id = complement.getId();
        this.title = complement.getTitle();
        this.content = complement.getContent();
        this.username = complement.getUsername();
    }
}
