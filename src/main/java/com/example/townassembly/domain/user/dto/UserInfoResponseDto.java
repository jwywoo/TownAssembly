package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String party;
    private String location;
    private String userIntro;
    private String nickname;
    private String imageUrl;

    public UserInfoResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.party = user.getParty();
        this.location = user.getLocation();
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public void setUserNickname(String nickname) { this.nickname = nickname; }


}
