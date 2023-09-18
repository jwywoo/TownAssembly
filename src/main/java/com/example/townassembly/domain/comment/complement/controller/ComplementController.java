package com.example.townassembly.domain.comment.complement.controller;

import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.dto.ComplementResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.comment.complement.service.ComplementService;
import com.example.townassembly.global.dto.StringResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ComplementResponseDto complementCreate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto){
        return complementService.complementCreate(id, requestDto);
    }
    // Read
    @GetMapping("/complements")
    public List<ComplementResponseDto> complementList(){
        // by user
        String username = "test";
        return complementService.complementList(username);
    }
    @GetMapping("/complement/{id}")
    public ComplementResponseDto complementDetail(@PathVariable Long id){
        return complementService.complementDetail(id);
    }
    // Update
    @PutMapping("/complement/{id}")
    public ComplementResponseDto complementUpdate(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto){
        return complementService.complementUpdate(id, requestDto);
    }
    // Delete
    @DeleteMapping("/complement/{id}")
    public StringResponseDto complementDelete(@PathVariable Long id, @RequestBody ComplementRequestDto requestDto){
        return complementService.complementDelete(id, requestDto);
    }
}
