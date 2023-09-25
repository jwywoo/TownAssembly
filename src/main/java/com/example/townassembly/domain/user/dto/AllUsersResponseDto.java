package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AllUsersResponseDto {
    private final Long userId;
    private final String nickname;
    private final String party;
    private final String location;
    private final UserRoleEnum role;
    private final String opinionTitle;

    public AllUsersResponseDto(User user, String opinionTitle) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.party = user.getParty();
        this.location = user.getLocation();
        this.role = user.getRole();
        this.opinionTitle = opinionTitle;
    }
}
