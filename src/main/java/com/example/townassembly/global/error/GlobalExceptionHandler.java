package com.example.townassembly.global.error;

import com.example.townassembly.global.dto.ErrorResponseDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ParameterValidationException.class)
    public ResponseEntity<ErrorResponseDto> parameterValidationExHandler(
            ParameterValidationException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDto> jwtExHandler(
            JwtException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> illegalArgumentExHandler(
            IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> usernameNotFoundExHandler(
            UsernameNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> runtimeExHandler(RuntimeException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exHandler(
            Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
