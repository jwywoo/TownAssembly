package com.example.townassembly.domain.post.campaign.service;

import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.campaign.repository.CampaignRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic="CampaignService")
public class CampaignService {
    private final CampaignRepository campaignRepository;
    public CampaignResponseDto campaignCreate(CampaignRequestDto requestDto) {
        Campaign campaign = campaignRepository.save(
                new Campaign(
                        requestDto,
                        "test"
                )
        );
        return new CampaignResponseDto(campaign);
    }

    public List<CampaignResponseDto> campaignList(String username) {
        return campaignRepository
                .findAllByUsernameOrderByModifiedAtDesc(username)
                .stream()
                .map(CampaignResponseDto::new)
                .toList();
    }

    public CampaignResponseDto campaignDetail(Long id) {
        return  new CampaignResponseDto(findById(id));
    }

    @Transactional
    public CampaignResponseDto campaignUpdate(Long id, CampaignRequestDto requestDto) {
        Campaign campaign = findById(id);
        campaign.update(requestDto);
        return new CampaignResponseDto(campaign);
    }

    public StringResponseDto campaignDelete(Long id) {
        Campaign campaign = findById(id);
        campaignRepository.delete(campaign);
        return new StringResponseDto("삭제성공", "200");
    }

    private Campaign findById(Long id) {
        return campaignRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("유효하지 않은 정보입니다.")
                );
    }
}
