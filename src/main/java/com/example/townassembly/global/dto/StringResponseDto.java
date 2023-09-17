package com.example.townassembly.global.dto;


import lombok.Getter;

@Getter
public class StringResponseDto {
    private final String msg;
    private final String status;
    public StringResponseDto(String msg, String status) {
        this.msg = msg;
        this.status = status;
    }
}
