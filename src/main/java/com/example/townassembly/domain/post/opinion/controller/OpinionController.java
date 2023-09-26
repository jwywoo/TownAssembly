package com.example.townassembly.domain.post.opinion.controller;

import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestModel;
import com.example.townassembly.domain.post.opinion.service.OpinionService;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "OpinionController")
public class OpinionController {
    private final OpinionService opinionService;

    // Create
    @PostMapping(value = "/opinion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResponseDto> opinionCreate(
            @RequestParam(value = "image") MultipartFile image,
            @ModelAttribute OpinionRequestModel requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.opinionCreate(requestDto, user, image)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
    // Read
    // User's opinionList
    @GetMapping("/opinions")
    public ResponseEntity<JsonResponseDto> opinionList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.opinionList(user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
    // Specific User's opinionList
    @GetMapping("/opinions/{id}")
    public ResponseEntity<JsonResponseDto> selectedUserOpinionList(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.selectedUserOpinionList(id,user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // User Detail
    @GetMapping("/opinion")
    public ResponseEntity<JsonResponseDto> selectedUserOpinionDetail(
            @RequestParam("opinionId") Long opinionId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.selectedUserOpinionDetail(opinionId, user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Update
    @PutMapping(value = "/opinion/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResponseDto> opinionUpdate(@PathVariable Long id,
                                                         @RequestParam(value = "image") MultipartFile image,
                                                         @ModelAttribute OpinionRequestModel requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.opinionUpdate(id, requestDto, user, image)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/opinion/{id}")
    public ResponseEntity<JsonResponseDto> opinionDelete(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            opinionService.opinionDelete(id, user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
}
