package com.example.townassembly.domain.comment.complement.controller;

import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.service.ComplementService;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "ComplementController")
public class ComplementController {
    private final ComplementService complementService;

    // Create
    @PostMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementCreate(
            @PathVariable Long id,
            @RequestBody ComplementRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(
                    HttpStatus.OK.value(),
                    complementService.complementCreate(id, requestDto, user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Read
    // User's complement
    @GetMapping("/complements")
    public ResponseEntity<JsonResponseDto> complementList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            complementService.complementList(user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
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
    public ResponseEntity<JsonResponseDto> complementDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            complementService.complementDetail(id, user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Update
    @PutMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementUpdate(
            @PathVariable Long id,
            @RequestBody ComplementRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            complementService.complementUpdate(id, requestDto, user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/complement/{id}")
    public ResponseEntity<JsonResponseDto> complementDelete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(
                    new JsonResponseDto(
                            HttpStatus.OK.value(),
                            complementService.complementDelete(id, user)
                    )
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
}
