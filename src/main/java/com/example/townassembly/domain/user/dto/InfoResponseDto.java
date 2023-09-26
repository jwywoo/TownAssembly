package com.example.townassembly.domain.user.dto;

import lombok.Getter;

@Getter
public class InfoResponseDto {
    private String userIntro;
    private String nickname;
    private String imageUrl;
    private Boolean followStat;

    public InfoResponseDto(String userIntro, String nickname, String imageUrl) {
        this.userIntro = userIntro;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public InfoResponseDto(String userIntro, String nickname, String imageUrl, Boolean followStat) {
        this.userIntro = userIntro;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.followStat = followStat;
    }
}
