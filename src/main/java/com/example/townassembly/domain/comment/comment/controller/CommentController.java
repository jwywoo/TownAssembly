package com.example.townassembly.domain.comment.comment.controller;

import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDtoList;
import com.example.townassembly.domain.comment.comment.service.CommentService;
import com.example.townassembly.global.dto.StringResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "CommentController")
public class CommentController {
    private final CommentService commentService;

    // Create
    @PostMapping("/comment/{id}")
    public CommentResponseDto commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.commentCreate(requestDto, id, userDetails.getUser());
    }
    // Read
    @GetMapping("/comments")
    public List<CommentResponseDtoList> commentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // by user
        String username = "test";
        return commentService.commentList(userDetails.getUser());
    }
    @GetMapping("/comment/{id}")
    public CommentResponseDto commentDetail(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.commentDetail(id, userDetails.getUser());
    }

    // Update
    @PutMapping("/comment/{id}")
    public CommentResponseDtoList commentUpdate(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.commentUpdate(id, requestDto, userDetails.getUser());
    }
    // Delete
    @DeleteMapping("/comment/{id}")
    public StringResponseDto stringResponseDto(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.commentDelete(id, userDetails.getUser());
    }

}
