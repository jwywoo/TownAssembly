package com.example.townassembly.domain.post.campaign.service;

import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.campaign.repository.CampaignRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CampaignService")
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    @Transactional
    public CampaignResponseDto campaignCreate(CampaignRequestDto requestDto, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        Campaign newCampaign = new Campaign(requestDto, user);
        user.campaignAdd(newCampaign);
        return new CampaignResponseDto(campaignRepository.save(newCampaign));
    }

    public List<CampaignResponseDto> campaignList(User user) {
        // 변경 예정
        return campaignRepository
                .findAllByUsernameOrderByCreatedAtDesc(user.getUsername())
                .stream()
                .map(CampaignResponseDto::new)
                .toList();
    }

    public List<CampaignResponseDto> selectedCampaignList(Long id) {
        User selectedUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 사용자입니다.")
        );
        return campaignRepository
                .findAllByUserOrderByCreatedAt(selectedUser)
                .stream()
                .map(CampaignResponseDto::new)
                .toList();
    }

    public CampaignResponseDto campaignDetail(Long id, User user) {
        Campaign selectedCampaign = null;
        for (Campaign campaign : user.getCampaignList()) {
            if (id.equals(campaign.getId())) {
                selectedCampaign = campaign;
            }
        }
        if (selectedCampaign == null) {
            throw new NullPointerException("유효하지 않은 활동입니다.");
        }
        return new CampaignResponseDto(selectedCampaign);
    }

    @Transactional
    public CampaignResponseDto campaignUpdate(Long id, CampaignRequestDto requestDto, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        Campaign campaign = findById(id);
        log.info(requestDto.getContent());
        if (campaign.getUsername().equals(user.getUsername())) {
            campaign.update(requestDto);
        } else {
            throw new IllegalArgumentException("수정이 불가능합니다.");
        }
        return new CampaignResponseDto(campaign);
    }

    public StringResponseDto campaignDelete(Long id, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        Campaign campaign = findById(id);
        if (campaign.getUsername().equals(user.getUsername())) {
            campaignRepository.delete(campaign);
        } else {
            throw new IllegalArgumentException("삭제가 불가능합니다.");
        }
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
