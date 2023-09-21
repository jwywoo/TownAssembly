package com.example.townassembly.domain.user.dto;

import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private String userIntro;
    private String userProfilePicture;
    private String nickname;

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public void setUserNickname(String nickname) {
        this.nickname = nickname;
    }
}
