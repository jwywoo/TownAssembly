package com.example.townassembly.domain.post.opinion.controller;

import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.service.OpinionService;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "OpinionController")
public class OpinionController {
    private final OpinionService opinionService;

    // Create
    @PostMapping("/opinion")
    public ResponseEntity<JsonResponseDto> opinionCreate(@RequestBody OpinionRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.opinionCreate(requestDto, userDetails.getUser())
                )
        );
    }
    // Read
    // User's opinionList
    @GetMapping("/opinions")
    public ResponseEntity<JsonResponseDto> opinionList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.opinionList(userDetails.getUser())
                        )
        );
    }
    // Specific User's opinionList
    @GetMapping("/opinions/{id}")
    public ResponseEntity<JsonResponseDto> selectedOpinionList(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.selectedUserOpinionList(id, userDetails.getUser())
                        )
        );
    }

    // User Detail
    @GetMapping("/opinions")
    public ResponseEntity<JsonResponseDto> selectedUserOpinionDetail(
            @RequestParam("opinionId") Long opinionId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.selectedUserOpinionDetail(opinionId, userDetails.getUser())
                        )
        );
    }

    // Update
    @PutMapping("/opinion/{id}")
    public ResponseEntity<JsonResponseDto> opinionUpdate(@PathVariable Long id, @RequestBody OpinionRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.opinionUpdate(id, requestDto, userDetails.getUser())
                        )
        );
    }

    // Delete
    @DeleteMapping("/opinion/{id}")
    public ResponseEntity<JsonResponseDto> opinionDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        opinionService.opinionDelete(id, userDetails.getUser())
                        )
        );
    }
}
