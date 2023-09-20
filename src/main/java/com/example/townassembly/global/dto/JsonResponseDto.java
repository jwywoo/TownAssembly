package com.example.townassembly.global.dto;


import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
public class JsonResponseDto {
    private int status;
    private Object data;
    public JsonResponseDto(int status,Object data) {
        this.status = status;
        this.data = data;
    }
}
