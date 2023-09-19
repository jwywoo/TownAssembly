package com.example.townassembly.domain.comment.complement.service;


import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.complement.dto.ComplementRequestDto;
import com.example.townassembly.domain.comment.complement.dto.ComplementResponseDto;
import com.example.townassembly.domain.comment.complement.entity.Complement;
import com.example.townassembly.domain.comment.complement.repository.ComplementRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ComplementService")
public class ComplementService {
    private final ComplementRepository complementRepository;
    private final UserRepository userRepository;
    public ComplementResponseDto complementCreate(Long id, ComplementRequestDto requestDto, User user) {
        User forWhom = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );
        Complement complement = complementRepository.save(
                new Complement(
                        requestDto,
                        user
                )
        );
        user.complementAdd(complement, forWhom);
        return new ComplementResponseDto(complement);
    }

    public List<ComplementResponseDto> complementList(User user) {
        return complementRepository
                .findAllByUsernameOrderByModifiedAtDesc(user.getUsername())
                .stream()
                .map(ComplementResponseDto::new)
                .toList();
    }

    public ComplementResponseDto complementDetail(Long id, User user) {
        Complement selectedComplement = null;
        for (Complement complement:user.getComplementList()) {
            if (id.equals(complement.getId())) selectedComplement = complement;
        }
        if (selectedComplement == null) throw new IllegalArgumentException("유효하지 않은 댓글입니다.");
        return new ComplementResponseDto(selectedComplement);
    }

    @Transactional
    public ComplementResponseDto complementUpdate(Long id, ComplementRequestDto requestDto, User user) {
        Complement complement = findById(id);
        if (user.getUsername().equals(complement.getUsername())) {
            complement.update(requestDto);
        } else {
            throw new IllegalArgumentException("수정할 수 없습니다.");
        }
        return new ComplementResponseDto(complement);
    }

    public StringResponseDto complementDelete(Long id, User user) {
        Complement complement = findById(id);
        if (user.getUsername().equals(complement.getUsername())) {
            complementRepository.delete(complement);
        } else {
            throw new IllegalArgumentException("삭제할 수 없습니다.");
        }
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
