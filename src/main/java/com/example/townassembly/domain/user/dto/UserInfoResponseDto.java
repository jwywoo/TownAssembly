package com.example.townassembly.domain.user.dto;

import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private String userIntro;
    private String userProfilePicture;

    public void setUserIntro(String userIntro) {
        this.userIntro = "블라블라블라 약력입니다";
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = "프로필 사진입니다";
    }
}
