package com.example.townassembly.domain.post.campaign.controller;


import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.service.CampaignService;
import com.example.townassembly.global.dto.StringResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CampaignResponseDto campaignCreate(@RequestBody CampaignRequestDto requestDto) {
        return campaignService.campaignCreate(requestDto);
    }
    // Read
    @GetMapping("/campaigns")
    public List<CampaignResponseDto> campaignList() {
        // by user
        String username = "test";
        return campaignService.campaignList(username);
    }

    @GetMapping("/campaign/{id}")
    public CampaignResponseDto campaignDetail(@PathVariable Long id) {
        return campaignService.campaignDetail(id);
    }

    // Update
    @PutMapping("/campaign/{id}")
    public CampaignResponseDto campaignUpdate(@PathVariable Long id, CampaignRequestDto requestDto) {
        return campaignService.campaignUpdate(id, requestDto);
    }

    // Delete
    @DeleteMapping("/campaign/{id}")
    public StringResponseDto campaignDelete(@PathVariable Long id) {
        return campaignService.campaignDelete(id);
    }
}
