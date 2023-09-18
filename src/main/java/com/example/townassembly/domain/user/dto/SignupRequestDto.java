package com.example.townassembly.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "ID는 4~10자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "ID는 소문자, 숫자만 가능합니다.")
    private String username;

    @Size(min = 8, max = 15, message = "비밀번호는 8~15자리까지만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]*$", message = "비밀번호는 대/소문자, 숫자,특수문자 가능합니다.")
    private String password;

//    private boolean adminCheck;

    private boolean admin = false;
    private String adminToken = "";
}
