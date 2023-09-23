package com.example.townassembly.domain.user.controller;

import com.example.townassembly.domain.user.dto.ModifyPasswordRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoRequestDto;
import com.example.townassembly.domain.user.service.UserProfileService;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // 내 정보 수정 페이지에 들어갔을 때 필요한 데이터를 불러온다.
    @GetMapping(value = "/modify")
    public ResponseEntity<JsonResponseDto> modifyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(),
                userProfileService.modifyProfile(userDetails.getUser())));
    }

    // 내 정보 수정에서 '수정'버튼을 누르면 기존에 저장된 데이터를 업데이트 한다.
    @PutMapping(value = "/modify/save")
    public ResponseEntity<JsonResponseDto> modifyProfileSave(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(),
                userProfileService.modifyProfileSave(userDetails.getUser(), userInfoRequestDto)));
    }

    // 내 정보 수정에서 프로필 이미지 업로드 기능
    @PutMapping(value = "/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResponseDto> uploadProfileImage(
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), userProfileService.uploadProfileImage(userDetails.getUser(), image)));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "프로필을 업데이트할 수 없습니다."));
        }
    }

    // 내 정보 수정에서 비밀번호 수정하기
    @PutMapping("/modify/password")
    public ResponseEntity<JsonResponseDto> modifyPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ModifyPasswordRequestDto modifyPasswordRequestDto) {
        return userProfileService.modifyPassword(userDetails.getUser(), modifyPasswordRequestDto);
    }
}
