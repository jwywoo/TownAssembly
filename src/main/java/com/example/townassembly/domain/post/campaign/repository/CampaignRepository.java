package com.example.townassembly.domain.post.campaign.repository;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}