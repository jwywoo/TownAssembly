package com.example.townassembly.domain.comment.complement.dto;

import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class ComplementResponseDto {
    private final Long complementId;
    private final String complementNickname;
    private final String complementTitle;
    private final String complementContent;
    private final UserRoleEnum complementRole;

    public ComplementResponseDto(Complement complement) {
        this.complementId = complement.getId();
        this.complementNickname = complement.getNickname();
        this.complementTitle = complement.getTitle();
        this.complementContent = complement.getContent();
        this.complementRole = complement.getRole();
    }
}
