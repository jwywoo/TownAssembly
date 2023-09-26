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

@Slf4j
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

    @GetMapping("/user/follow/{id}")
    public ResponseEntity<JsonResponseDto> followUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            boolean followSuccess = userService.followUser(id, user);
            if (followSuccess) {
                return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), "팔로우 성공"));
            } else {
                return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), "이미 팔로우한 사용자입니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }

    @PostMapping("/user/unfollow/{id}")
    public ResponseEntity<JsonResponseDto> unfollowUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            boolean unfollowSuccess = userService.unfollowUser(id, user);
            if (unfollowSuccess) {
                return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), "언팔로우 성공"));
            } else {
                return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), "이미 언팔로우한 사용자입니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseDto(HttpStatus.FORBIDDEN.value(), "유효하지 않은 회원정보입니다."));
        }
    }
}
