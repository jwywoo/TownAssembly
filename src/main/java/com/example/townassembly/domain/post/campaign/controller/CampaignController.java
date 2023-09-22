package com.example.townassembly.domain.post.campaign.controller;


import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.service.CampaignService;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Campaign Controller")
public class CampaignController {
    private final CampaignService campaignService;

    // Create with Pics
    @PostMapping(value = "/campaign", consumes = {"multipart/form-data"})
    public ResponseEntity<JsonResponseDto> campaignCreate(
            @RequestParam(value = "image") MultipartFile image,
            @RequestBody CampaignRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        return ResponseEntity.ok(new JsonResponseDto(
                        HttpStatus.OK.value(),
                        campaignService.campaignCreate(requestDto, userDetails.getUser(), image)
                )
        );
    }

//     Create
//    @PostMapping("/campaign")
//    public ResponseEntity<JsonResponseDto> campaignCreate(@RequestBody CampaignRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return ResponseEntity.ok(new JsonResponseDto(
//                HttpStatus.OK.value(),
//                campaignService.campaignCreate(requestDto, userDetails.getUser())
//        ));
//    }

    // Read
    // User's campaign
    @GetMapping("/campaigns")
    public ResponseEntity<JsonResponseDto> campaignList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(
                HttpStatus.OK.value(),
                campaignService.campaignList(userDetails.getUser())
        ));
    }

    // Selected User's campaign
    @GetMapping("/campaigns/{id}")
    public ResponseEntity<JsonResponseDto> selectedCampaignList(@PathVariable Long id) {
        return ResponseEntity.ok(new JsonResponseDto(
                HttpStatus.OK.value(),
                campaignService.selectedCampaignList(id)
        ));
    }

    @GetMapping("/campaign/{id}")
    public ResponseEntity<JsonResponseDto> campaignDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(
                HttpStatus.OK.value(),
                campaignService.campaignDetail(id, userDetails.getUser())
        ));
    }

    // Update
    @PutMapping("/campaign/{id}")
    public ResponseEntity<JsonResponseDto> campaignUpdate(@PathVariable Long id, @RequestBody CampaignRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(requestDto.getContent());
        return ResponseEntity.ok(new JsonResponseDto(
                HttpStatus.OK.value(),
                campaignService.campaignUpdate(id, requestDto, userDetails.getUser())
        ));
    }

    // Delete
    @DeleteMapping("/campaign/{id}")
    public ResponseEntity<JsonResponseDto> campaignDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new JsonResponseDto(
                HttpStatus.OK.value(),
                campaignService.campaignDelete(id, userDetails.getUser())
        ));
    }
}
