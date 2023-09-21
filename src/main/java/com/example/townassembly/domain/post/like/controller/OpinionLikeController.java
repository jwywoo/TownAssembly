package com.example.townassembly.domain.post.like.controller;

import com.example.townassembly.domain.post.like.service.OpinionLikeService;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Opinion Like")
public class OpinionLikeController {
    private final OpinionLikeService opinionLikeService;

    @PostMapping("/opinion/like/{id}")
    public void opinionLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        opinionLikeService.opinionLike(id, userDetails.getUser());
    }
}
