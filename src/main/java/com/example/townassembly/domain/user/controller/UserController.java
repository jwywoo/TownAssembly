package com.example.townassembly.domain.user.controller;

import com.example.townassembly.domain.user.dto.AllUsersResponseDto;
import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.follow.entity.Follow;
import com.example.townassembly.domain.user.repository.UserRepository;
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

@Slf4j(topic = "야 여기다")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

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
    public ResponseEntity<JsonResponseDto> LocationUsersList(@RequestParam("location") String location,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            List<AllUsersResponseDto> usersLocationDtos = userService.LocationUsersList(location, user);
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), usersLocationDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }

    @GetMapping("/user/party")
    public ResponseEntity<JsonResponseDto> PartyUsersList(@RequestParam("party") String party,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            List<AllUsersResponseDto> usersPartyDtos = userService.PartyUsersList(party, user);
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), usersPartyDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }

    @GetMapping("/user/following")
    public ResponseEntity<JsonResponseDto> FollowingUsersList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {

            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userService.FollowingUsersList(user)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }

    @GetMapping("/user/userInfo")
    public ResponseEntity<JsonResponseDto> UserInfoList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userService.userInfoList(user)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<JsonResponseDto> userStatus(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            log.info(id+"여기다");
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userService.userStatus(id, user)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }
}
