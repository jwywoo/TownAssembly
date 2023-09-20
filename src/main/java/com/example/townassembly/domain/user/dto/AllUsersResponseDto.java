package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.User;
import lombok.Getter;

@Getter
public class AllUsersResponseDto {
    private final String username;
    private final String party;
    private final String location;
    private final String opinionTitle;

    public AllUsersResponseDto(User user, Opinion opinion) {
        this.username = user.getUsername();
        this.party = user.getParty();
        this.location = user.getLocation();
        this.opinionTitle = opinion.getTitle();
    }
}
