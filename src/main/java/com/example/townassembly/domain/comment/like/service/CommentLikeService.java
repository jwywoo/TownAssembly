package com.example.townassembly.domain.comment.like.service;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.comment.repository.CommentRepository;
import com.example.townassembly.domain.comment.like.dto.CommentLikeRequestDto;
import com.example.townassembly.domain.comment.like.entity.CommentLike;
import com.example.townassembly.domain.comment.like.repository.CommentLikeRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void commentLike(CommentLikeRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 계정입니다.")
        );

        Comment comment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 댓글입니다.")
        );

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment);

        if (commentLike == null) {
            commentLike = new CommentLike();
            commentLike.setComment(comment);
            commentLike.setUser(user);
            comment.increaseCnt();
            commentLikeRepository.save(commentLike);
        } else {
            comment.decreaseCnt();
            commentLikeRepository.delete(commentLike);
        }
    }
}
