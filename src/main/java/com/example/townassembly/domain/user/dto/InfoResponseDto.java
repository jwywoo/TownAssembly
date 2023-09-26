package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.user.follow.entity.Follow;
import lombok.Getter;

import java.util.List;

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

    public InfoResponseDto(List<Follow> followList) {
    }
}
