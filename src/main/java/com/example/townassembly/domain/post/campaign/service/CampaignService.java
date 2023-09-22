package com.example.townassembly.domain.post.campaign.service;

import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignRequestModel;
import com.example.townassembly.domain.post.campaign.dto.CampaignResponseDto;
import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.campaign.repository.CampaignRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import com.example.townassembly.global.s3.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CampaignService")
public class CampaignService {
    @Autowired
    private S3Uploader s3Uploader;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    @Transactional
    public CampaignResponseDto campaignCreate(CampaignRequestModel requestDto, User user, MultipartFile image) throws IOException {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        if (!image.isEmpty()) {
            String fileName = s3Uploader.upload(image, "campaign");
            Campaign newCampaign = new Campaign(requestDto, user, fileName);
            user.campaignAdd(newCampaign);
            return new CampaignResponseDto(campaignRepository.save(newCampaign));
        } else {
            throw new IOException("사진을 추가하여 주세요");
        }

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
