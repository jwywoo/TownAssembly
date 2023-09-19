package com.example.townassembly.domain.post.campaign.controller;


import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.service.CampaignService;
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
@Slf4j(topic = "Campaign Controller")
public class CampaignController {
    private final CampaignService campaignService;

    // Create
    @PostMapping("/campaign")
    public CampaignResponseDto campaignCreate(@RequestBody CampaignRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return campaignService.campaignCreate(requestDto, userDetails.getUser());
    }
    // Read
    @GetMapping("/campaigns")
    public List<CampaignResponseDto> campaignList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return campaignService.campaignList(userDetails.getUser());
    }

    @GetMapping("/campaign/{id}")
    public CampaignResponseDto campaignDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return campaignService.campaignDetail(id, userDetails.getUser());
    }

    // Update
    @PutMapping("/campaign/{id}")
    public CampaignResponseDto campaignUpdate(@PathVariable Long id, CampaignRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return campaignService.campaignUpdate(id, requestDto, userDetails.getUser());
    }

    // Delete
    @DeleteMapping("/campaign/{id}")
    public StringResponseDto campaignDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return campaignService.campaignDelete(id, userDetails.getUser());
    }
}
