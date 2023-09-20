package com.example.townassembly.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(min = 6, max = 20, message = "ID는 6~20자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "ID는 소문자, 숫자만 가능합니다.")
    @NotBlank
    private String username;

    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!#%@?]*$", message = "비밀번호는 대/소문자, 숫자,특수문자 가능합니다.")
    @NotBlank
    private String password;

    private String party = "";
    private String location = "";
    private String nickname;

    @Email
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "이메일은 영문 대/소문자, 숫자만 입력 가능합니다.")
    private String email;

    private boolean admin = false;
    private String adminToken = "";
    private boolean voterUser = false;
}
