package com.example.townassembly.domain.comment.like.controller;

import com.example.townassembly.domain.comment.like.dto.CommentLikeRequestDto;
import com.example.townassembly.domain.comment.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Comment Like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    @PostMapping("/comment/like")
    public void commentLike(@RequestBody CommentLikeRequestDto requestDto) {
        commentLikeService.commentLike(requestDto);
    }
}
