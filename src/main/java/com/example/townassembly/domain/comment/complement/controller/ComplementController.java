package com.example.townassembly.domain.comment.complement.controller;

import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.dto.ComplementResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.comment.complement.service.ComplementService;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.dto.StringResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "ComplementController")
public class ComplementController {
    private final ComplementService complementService;

    // Create
    @PostMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementCreate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), complementService.complementCreate(id, requestDto, userDetails.getUser())));
    }

    // Read
    // User's complement
    @GetMapping("/complements")
    public ResponseEntity<JsonResponseDto> complementList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        complementService.complementList(userDetails.getUser()
                        )
                )
        );
    }

    // Specific User's complement
    @GetMapping("/complements/{id}")
    public ResponseEntity<JsonResponseDto> selectedComplementList(@PathVariable Long id) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        complementService.selectedComplementList(id)
                        )
        );
    }

    @GetMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        complementService.complementDetail(id, userDetails.getUser())
                        )
        );
    }

    // Update
    @PutMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementUpdate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        complementService.complementUpdate(id, requestDto, userDetails.getUser())
                        )
        );
    }

    // Delete
    @DeleteMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementDelete(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new JsonResponseDto(
                        HttpStatus.OK.value(),
                        complementService.complementDelete(id, userDetails.getUser())
                        )
        );
    }
}
