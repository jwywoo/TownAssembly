package com.example.townassembly.domain.user.controller;

import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoResponseDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.service.UserService;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<JsonResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(),userService.signup(requestDto)));
    }

    @GetMapping("/user/main")
    public ResponseEntity<JsonResponseDto> AllUsersList(UserRoleEnum userRoleEnum) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userService.AllUsersList(userRoleEnum)));
    }

    @GetMapping("/user/location")
    public ResponseEntity<JsonResponseDto> LocationUsersList(@RequestParam("location") String location) {
        return userService.LocationUsersList(location);
    }

    @GetMapping("/user/party")
    public ResponseEntity<JsonResponseDto> PartyUsersList(@RequestParam("party") String party) {
        return userService.PartyUsersList(party);
    }

    @GetMapping("/user/following")
    public ResponseEntity<JsonResponseDto>  FollowingUsersList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userService.FollowingUsersList(user)));
    }

    @GetMapping("/user/userinfo")
    public ResponseEntity<List<UserInfoResponseDto>> UserInfoList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.UserInfoList(user);
    }
}
