package com.example.townassembly.domain.user.controller;

import com.example.townassembly.domain.user.dto.UserInfoRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoRequestModel;
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

    @GetMapping(value = "/modify")
    public ResponseEntity<JsonResponseDto> modifyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(),
                userProfileService.modifyProfile(userDetails.getUser())));
    }

    @PutMapping(value = "/modify/save")
    public ResponseEntity<JsonResponseDto> modifyProfileSave(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(),
                userProfileService.modifyProfileSave(userDetails.getUser(), userInfoRequestDto)));
    }

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
}
