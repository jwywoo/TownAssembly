package com.example.townassembly.domain.post.opinion.controller;

import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.service.OpinionService;
import com.example.townassembly.global.dto.StringResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public OpinionResponseDto opinionCreate(@RequestBody OpinionRequestDto requestDto){
        return opinionService.opinionCreate(requestDto);
    }
    // Read
    @GetMapping("/opinion")
    public List<OpinionResponseDto> opinionList(){
        // by user
        String username = "test";
        return opinionService.opinionList(username);
    }

    @GetMapping("/opinion/{id}")
    public OpinionResponseDto opinionDetail(@PathVariable Long id){
        return opinionService.opinionDetail(id);
    }

    // Update
    @PutMapping("/opinion/{id}")
    public OpinionResponseDto opinionUpdate(@PathVariable Long id, @RequestBody OpinionRequestDto requestDto){
        return opinionService.opinionUpdate(id, requestDto);
    }

    // Delete
    @DeleteMapping("/opinion/{id}")
    public StringResponseDto opinionDelete(@PathVariable Long id){
        return opinionService.opinionDelete(id);
    }
}
