package com.example.townassembly.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(min = 6, max = 20, message = "ID는 6~20자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "ID는 소문자, 숫자만 가능합니다.")
    private String username;

    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!#%@?]*$", message = "비밀번호는 대/소문자, 숫자,특수문자 가능합니다.")
    private String password;

    @Size(min = 8, max = 20, message = "비밀번호 확인은 8~20자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!#%@?]*$", message = "비밀번호 확인은 대/소문자, 숫자,특수문자 가능합니다.")
    private String passwordConfirm;

    private String party = "";

    private String location = "";

    private boolean admin = false;
    private String adminToken = "";

    private boolean voterUser = false;
}
