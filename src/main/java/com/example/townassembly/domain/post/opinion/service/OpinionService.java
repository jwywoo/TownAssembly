package com.example.townassembly.domain.post.opinion.service;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "OpinionService")
public class OpinionService {
    private final OpinionRepository opinionRepository;

    public OpinionResponseDto opinionCreate(OpinionRequestDto requestDto, User user) {
        Opinion opinion = opinionRepository.save(
                new Opinion(
                        requestDto,
                        user
                )
        );
        user.opinionAdd(opinion);
        return new OpinionResponseDto(opinion);
    }

    public List<OpinionResponseDto> opinionList(User user) {
        return opinionRepository
                .findAllByUsernameOrderByModifiedAtDesc(user.getUsername())
                .stream()
                .map(OpinionResponseDto::new)
                .toList();
    }

    public OpinionResponseDto opinionDetail(Long id, User user) {
        Opinion selectedOpinion = null;
        for (Opinion opinion: user.getOpinionList()) {
            if (id.equals(opinion.getId())) {
                selectedOpinion = opinion;
            }
        }
        if (selectedOpinion == null) {
            throw new NullPointerException("유효하지 않은 활동입니다.");
        }
        return new OpinionResponseDto(findById(id));
    }

    @Transactional
    public OpinionResponseDto opinionUpdate(Long id, OpinionRequestDto requestDto, User user) {
        Opinion opinion = findById(id);
        if (opinion.getUsername().equals(user.getUsername())) {
            opinion.update(requestDto);
        } else {
            throw new IllegalArgumentException("수정이 불가능합니다.");
        }
        return new OpinionResponseDto(opinion);
    }

    public StringResponseDto opinionDelete(Long id, User user) {
        Opinion opinion = findById(id);
        if (opinion.getUsername().equals(user.getUsername())) {
            opinionRepository.delete(opinion);
        } else {
            throw new IllegalArgumentException("삭제가 불가능 합니다.");
        }
        return new StringResponseDto("삭제성공", "200");
    }

    private Opinion findById(Long id) {
        return opinionRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("유효하지 않은 정보입니다.")
                );
    }
}
