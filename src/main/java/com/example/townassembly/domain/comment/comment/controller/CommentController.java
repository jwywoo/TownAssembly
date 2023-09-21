package com.example.townassembly.domain.comment.comment.controller;

import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.service.CommentService;
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
@Slf4j(topic = "CommentController")
public class CommentController {
    private final CommentService commentService;

    // Create
    @PostMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentCreate(requestDto, id, userDetails.getUser())));
    }
    // Read
    @GetMapping("/comments")
    public ResponseEntity<JsonResponseDto> commentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // by user
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentList(userDetails.getUser())));
//        return commentService.commentList(userDetails.getUser());
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentDetail(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentDetail(id, userDetails.getUser())));
    }

    // Update
    @PutMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> commentUpdate(@PathVariable Long id,
                                                @RequestBody CommentRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentUpdate(id, requestDto, userDetails.getUser())));
    }
    // Delete
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<JsonResponseDto> stringResponseDto(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), commentService.commentDelete(id, userDetails.getUser())));
    }

}
