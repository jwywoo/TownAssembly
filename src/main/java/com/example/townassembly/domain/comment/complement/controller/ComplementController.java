package com.example.townassembly.domain.comment.complement.controller;

import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.dto.ComplementResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.comment.complement.service.ComplementService;
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
@Slf4j(topic = "ComplementController")
public class ComplementController {
    private final ComplementService complementService;
    // Create
    @PostMapping("/complement/{id}")
    public ComplementResponseDto complementCreate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return complementService.complementCreate(id, requestDto, userDetails.getUser());
    }
    // Read
    @GetMapping("/complements")
    public List<ComplementResponseDto> complementList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return complementService.complementList(userDetails.getUser());
    }
    @GetMapping("/complement/{id}")
    public ComplementResponseDto complementDetail(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return complementService.complementDetail(id, userDetails.getUser());
    }
    // Update
    @PutMapping("/complement/{id}")
    public ComplementResponseDto complementUpdate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return complementService.complementUpdate(id, requestDto, userDetails.getUser());
    }
    // Delete
    @DeleteMapping("/complement/{id}")
    public StringResponseDto complementDelete(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return complementService.complementDelete(id, userDetails.getUser());
    }
}
