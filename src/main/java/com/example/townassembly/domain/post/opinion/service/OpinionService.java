package com.example.townassembly.domain.post.opinion.service;

import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
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

    public OpinionResponseDto opinionCreate(OpinionRequestDto requestDto) {
        Opinion opinion = opinionRepository.save(
                new Opinion(
                        requestDto,
                        "test"
                )
        );
        return new OpinionResponseDto(opinion);
    }

    public List<OpinionResponseDto> opinionList(String username) {
        return opinionRepository
                .findAllByUsernameOrderByModifiedAtDesc(username)
                .stream()
                .map(OpinionResponseDto::new)
                .toList();
    }

    public OpinionResponseDto opinionDetail(Long id) {
        return new OpinionResponseDto(findById(id));
    }

    @Transactional
    public OpinionResponseDto opinionUpdate(Long id, OpinionRequestDto requestDto) {
        Opinion opinion = findById(id);
        opinion.update(requestDto);
        return new OpinionResponseDto(opinion);
    }

    public StringResponseDto opinionDelete(Long id) {
        Opinion opinion = findById(id);
        opinionRepository.delete(opinion);
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
