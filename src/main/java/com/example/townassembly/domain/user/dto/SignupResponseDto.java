package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private final Long userId;
    private final String username;
    private final String nickname;
    private final String party;
    private final String location;
    private final String email;
    private final UserRoleEnum role;

    public SignupResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.party = user.getParty();
        this.location = user.getLocation();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
