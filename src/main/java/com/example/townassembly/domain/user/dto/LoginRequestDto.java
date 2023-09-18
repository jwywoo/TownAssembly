package com.example.townassembly.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String voterId;
    private String voterPw;
}
