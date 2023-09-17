package com.example.townassembly.domain.post.campaign.dto;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import lombok.Getter;

@Getter
public class CampaignResponseDto {
    private final String title;
    private final String content;
    private final String url;
    private final String username;
    private final byte[] imageThumbnail;

    public CampaignResponseDto(Campaign campaign) {
        this.title = campaign.getTitle();
        this.content = campaign.getContent();
        this.url = campaign.getUrl();
        this.username = campaign.getUsername();
        this.imageThumbnail = campaign.getImageThumbnail();
    }
}
