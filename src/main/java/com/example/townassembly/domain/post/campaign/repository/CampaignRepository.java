package com.example.townassembly.domain.post.campaign.repository;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign>  findAllByUsernameOrderByCreatedAtDesc(String username);
    List<Campaign>  findAllByUserOrderByCreatedAt(User user);
}
