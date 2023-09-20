package com.example.townassembly.domain.post.opinion.controller;

import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDtoDetail;
import com.example.townassembly.domain.post.opinion.service.OpinionService;
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
@Slf4j(topic = "OpinionController")
public class OpinionController {
    private final OpinionService opinionService;

    // Create
    @PostMapping("/opinion")
    public OpinionResponseDto opinionCreate(@RequestBody OpinionRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return opinionService.opinionCreate(requestDto, userDetails.getUser());
    }
    // Read
    // User's opinionList
    @GetMapping("/opinions")
    public List<OpinionResponseDto> opinionList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return opinionService.opinionList(userDetails.getUser());
    }
    // Specific User's opinionList
    @GetMapping("/opinions/{id}")
    public List<OpinionResponseDto> selectedOpinionList(@PathVariable Long id) {
        return opinionService.selectedOpinionList(id);
    }

    // User's specific opinion
    @GetMapping("/opinion/{id}")
    public OpinionResponseDto opinionDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return opinionService.opinionDetail(id, userDetails.getUser());
    }
    // selected User's specific opinion
    @GetMapping("/opinion/{userid}/{opinionid}")
    public OpinionResponseDtoDetail selectedOpinionDetail(
            @PathVariable("userid") Long userid,
            @PathVariable("opinionid") Long opinionId) {
        return opinionService.selectedOpinionDetail(userid, opinionId);
    }

    // Update
    @PutMapping("/opinion/{id}")
    public OpinionResponseDto opinionUpdate(@PathVariable Long id, @RequestBody OpinionRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return opinionService.opinionUpdate(id, requestDto, userDetails.getUser());
    }

    // Delete
    @DeleteMapping("/opinion/{id}")
    public StringResponseDto opinionDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return opinionService.opinionDelete(id, userDetails.getUser());
    }
}
