package com.example.townassembly.domain.comment.comment.service;


import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.comment.repository.CommentRepository;
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
@Slf4j(topic = "CommentService")
public class CommentService {
    private final CommentRepository commentRepository;
    private final OpinionRepository opinionRepository;

    public CommentResponseDto commentCreate(CommentRequestDto requestDto, Long opinionId) {
        Opinion opinion = findByIdOpinion(opinionId);
        Comment comment = commentRepository.save(
                new Comment(
                        requestDto,
                        "test",
                        opinion
                )
        );
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> commentList(String username) {
        return this.commentRepository
                .findAllByUsernameOrderByModifiedAtDesc(username)
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public CommentResponseDto commentDetail(Long id) {
        return new CommentResponseDto(findById(id));
    }

    @Transactional
    public CommentResponseDto commentUpdate(Long id, CommentRequestDto requestDto) {
        Comment comment = findById(id);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public StringResponseDto commentDelete(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
        return new StringResponseDto("삭제성공", "200");
    }

    private Opinion findByIdOpinion(Long id) {
        return opinionRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("유효하지 않은 정보입니다.")
                );
    }

    private Comment findById(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("유효하지 않은 정보입니다.")
                );
    }
}
