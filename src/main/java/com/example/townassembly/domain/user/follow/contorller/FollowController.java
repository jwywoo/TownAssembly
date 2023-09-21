package com.example.townassembly.domain.user.follow.contorller;

import com.example.townassembly.domain.user.follow.dto.FollowRequestDto;
import com.example.townassembly.domain.user.follow.service.FollowService;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/user/follow")
    public void followCreate(@RequestBody FollowRequestDto followRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.followCreate(followRequestDto, userDetails.getUser());
    }
}
