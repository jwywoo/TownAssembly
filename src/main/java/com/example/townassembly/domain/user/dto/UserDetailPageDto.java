package com.example.townassembly.domain.user.dto;

import com.example.townassembly.domain.user.entity.User;
import lombok.Getter;

//[{
//”nickname”:””,
//”imgUrl” : “”,
//”userIntro” : “”,
//”followStat” : boolean
//}
//]
@Getter
public class UserDetailPageDto {
    private final Boolean followStat;
    private final String selectedUserProfile;
    private final String selectedUserIntro;
    private final String selectedUserNickname;

    public UserDetailPageDto(Boolean followStat,
                             User user) {
        this.followStat = followStat;
        this.selectedUserProfile = user.getImageUrl();
        this.selectedUserIntro = user.getUserIntro();
        this.selectedUserNickname = user.getNickname();
    }
}
