package com.example.townassembly.domain.comment.complement.dto;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import lombok.Getter;

@Getter
public class ComplementResponseDto {
    private final Long complementId;
    private final String complementNickname;
    private final String complementTitle;
    private final String complementContent;

    public ComplementResponseDto(Complement complement) {
        this.complementId = complement.getId();
        this.complementNickname = complement.getNickname();
        this.complementTitle = complement.getTitle();
        this.complementContent = complement.getContent();
    }
}
