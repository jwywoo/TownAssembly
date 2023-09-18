package com.example.townassembly.domain.comment.comment.controller;

import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.service.CommentService;
import com.example.townassembly.global.dto.StringResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CommentResponseDto commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.commentCreate(requestDto, id);
    }
    // Read
    @GetMapping("/comments")
    public List<CommentResponseDto> commentList() {
        // by user
        String username = "test";
        return commentService.commentList(username);
    }
    @GetMapping("/comment/{id}")
    public CommentResponseDto commentDetail(@PathVariable Long id) {
        return commentService.commentDetail(id);
    }

    // Update
    @PutMapping("/comment/{id}")
    public CommentResponseDto commentUpdate(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.commentUpdate(id, requestDto);
    }
    // Delete
    @DeleteMapping("/comment/{id}")
    public StringResponseDto stringResponseDto(@PathVariable Long id) {
        return commentService.commentDelete(id);
    }

}
