package com.example.townassembly.domain.user.dto;

import lombok.Data;

@Data
public class ModifyPasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
