package com.example.townassembly.domain.post.campaign.dto;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class CampaignResponseDto {
    private final Long campaignId;
    private final String campaignTitle;
    private final String campaignContent;
    private final String campaignUrl;
    private final String campaignNickname;
    private final String campaignThumbnail;
    private final UserRoleEnum campaignRole;

    public CampaignResponseDto(Campaign campaign) {
        this.campaignId = campaign.getId();
        this.campaignTitle = campaign.getTitle();
        this.campaignContent = campaign.getContent();
        this.campaignUrl = campaign.getUrl();
        this.campaignNickname = campaign.getNickname();
        this.campaignThumbnail = campaign.getImageUrl();
        this.campaignRole = campaign.getRole();
    }
}
