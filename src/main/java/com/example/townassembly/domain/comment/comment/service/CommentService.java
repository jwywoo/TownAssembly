package com.example.townassembly.domain.comment.comment.service;


import com.example.townassembly.domain.comment.comment.dto.CommentRequestDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.dto.CommentResponseDtoList;
import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.comment.repository.CommentRepository;
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
@Slf4j(topic = "CommentService")
public class CommentService {
    private final CommentRepository commentRepository;
    private final OpinionRepository opinionRepository;

    @Transactional
    public CommentResponseDto commentCreate(CommentRequestDto requestDto, Long opinionId, User user) {
        Opinion opinion = findByIdOpinion(opinionId);
        Comment newComment = new Comment(requestDto, user);
        user.commentAdd(newComment);
        opinion.commentAdd(newComment);
        return new CommentResponseDto(commentRepository.save(newComment), false, 0);
    }

    public List<CommentResponseDtoList> commentList(User user) {
        return commentRepository
                .findAllByUsernameOrderByModifiedAtDesc(user.getUsername()).stream().map(CommentResponseDtoList::new).toList();
    }

    public CommentResponseDto commentDetail(Long id, User user) {
        Comment selectedComment = null;
        for (Comment comment:user.getCommentList()) {
            if (id.equals(comment.getId())) selectedComment = comment;
        }
        if (selectedComment == null) throw new IllegalArgumentException("유효하지 않은 댓글입니다.");
        return new CommentResponseDto(selectedComment, false, 0);
    }

    @Transactional
    public CommentResponseDtoList commentUpdate(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = findById(id);
        if (user.getUsername().equals(comment.getUsername())) {
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("수정할 수 없습니다.");
        }
        return new CommentResponseDtoList(comment);
    }

    public StringResponseDto commentDelete(Long id, User user) {
        Comment comment = findById(id);
        if (user.getUsername().equals(comment.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("삭제할 수 없습니다.");
        }
        return new StringResponseDto("삭제성공", "200");
//        return "삭제성공";
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
