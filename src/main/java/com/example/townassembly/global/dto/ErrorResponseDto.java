package com.example.townassembly.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {
    private final int status;
    private final String msg;

    public ErrorResponseDto(int httpStatus, String message) {
        this.status = httpStatus;
        this.msg = message;
    }
}
