package com.example.townassembly.domain.comment.comment.controller;

import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.service.CommentService;
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
@Slf4j(topic = "CommentController")
public class CommentController {
    private final CommentService commentService;

    // Create
    @PostMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentCreate(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok().body(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentCreate(requestDto, id, user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Read
    @GetMapping("/comments")
    public ResponseEntity<JsonResponseDto> commentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // by user
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentList(user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentDetail(id, user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Update
    @PutMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentUpdate(@PathVariable Long id,
                                                         @RequestBody CommentRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentUpdate(id, requestDto, user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentDelete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentDelete(id, user)));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

}
