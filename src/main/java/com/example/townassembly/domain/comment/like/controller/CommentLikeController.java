package com.example.townassembly.domain.comment.like.controller;

import com.example.townassembly.domain.comment.like.dto.CommentLikeRequestDto;
import com.example.townassembly.domain.comment.like.service.CommentLikeService;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Comment Like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    @PostMapping("/comment/like/{id}")
    public void commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.commentLike(id,userDetails.getUser());
    }
}
