package com.example.townassembly.domain.comment.complement.service;


import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.dto.ComplementResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.comment.complement.repository.ComplementRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ComplementService")
public class ComplementService {
    private final ComplementRepository complementRepository;
    public ComplementResponseDto complementCreate(Long id, ComplementRequestDto requestDto) {
        Complement complement = complementRepository.save(
                new Complement(
                        requestDto,
                        "test"
                )
        );
        return new ComplementResponseDto(complement);
    }

    public List<ComplementResponseDto> complementList(String username) {
        return complementRepository
                .findAllByUsernameOrderByModifiedAtDesc(username)
                .stream()
                .map(ComplementResponseDto::new)
                .toList();
    }

    public ComplementResponseDto complementDetail(Long id) {
        return new ComplementResponseDto(
                findById(id)
        );
    }

    @Transactional
    public ComplementResponseDto complementUpdate(Long id, ComplementRequestDto requestDto) {
        Complement complement = findById(id);
        complement.update(requestDto);
        return new ComplementResponseDto(complement);
    }

    public StringResponseDto complementDelete(Long id, ComplementRequestDto requestDto) {
        Complement complement = findById(id);
        complementRepository.delete(complement);
        return new StringResponseDto("삭제성공", "200");
    }

    private Complement findById(Long id) {
        return complementRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("유효하지 않은 정보입니다.")
                );
    }
}
