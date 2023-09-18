package com.example.townassembly.domain.user.controller;

import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.service.VoterUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class VoterUserController {

    private final VoterUserService voterUserService;

    public VoterUserController(VoterUserService voterUserService) {this.voterUserService = voterUserService;}

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                ResponseEntity.badRequest().body(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            }
        }
        voterUserService.signup(requestDto);
        return ResponseEntity.ok().body(String.valueOf(HttpStatus.OK.value()));
    }



}