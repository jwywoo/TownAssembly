package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String party;
    private String location;
    private String userIntro;
    private String nickname;
    private String imageUrl;
    private UserRoleEnum role;

    public UserInfoResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.party = user.getParty();
        this.location = user.getLocation();
        this.role = user.getRole();
        this.userIntro = user.getUserIntro();
        this.nickname = user.getNickname();
    }

    public void setUserNickname(String nickname) { this.nickname = nickname; }

    public void setEmail(String email) { this.email = email;}

    public void setParty(String party) {this.party = party;}

    public void setUserIntro(String userIntro) { this.userIntro = userIntro;}
}
