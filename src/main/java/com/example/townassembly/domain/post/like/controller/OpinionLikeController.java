package com.example.townassembly.domain.post.like.controller;

import com.example.townassembly.domain.comment.like.repository.CommentLikeRepository;
import com.example.townassembly.domain.post.like.dto.OpinionLikeRequestDto;
import com.example.townassembly.domain.post.like.service.OpinionLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Opinion Like")
public class OpinionLikeController {
    private final OpinionLikeService opinionLikeService;

    @PostMapping("/opinion/like")
    public void opinionLike(@RequestBody OpinionLikeRequestDto requestDto) {
        log.info(""+requestDto.getOpinionId());
        log.info(""+requestDto.getUserId());
        opinionLikeService.opinionLike(requestDto);
    }
}
