package com.example.townassembly.domain.post.opinion.service;

import com.example.townassembly.domain.comment.comment.dto.CommentResponseDto;
import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.comment.repository.CommentRepository;
import com.example.townassembly.domain.comment.like.entity.CommentLike;
import com.example.townassembly.domain.comment.like.repository.CommentLikeRepository;
import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.like.repository.OpinionLikeRepository;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDtoDetail;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDtoList;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.dto.StringResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "OpinionService")
public class OpinionService {
    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;
    private final OpinionLikeRepository opinionLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public OpinionResponseDto opinionCreate(OpinionRequestDto requestDto, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        Opinion newOpinion = new Opinion(requestDto, user);
        user.opinionAdd(newOpinion);
        return new OpinionResponseDto(opinionRepository.save(newOpinion));
    }

    public List<OpinionResponseDto> opinionList(User user) {
        return opinionRepository
                .findAllByUsernameOrderByCreatedAtDesc(user.getUsername())
                .stream()
                .map(OpinionResponseDto::new)
                .toList();
    }

    public List<OpinionResponseDtoList> selectedUserOpinionList(Long id, User user) {
        User selectedUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않는 사용자 입니다.")
        );
        List<Opinion> opinionList = opinionRepository.findAllByUserOrderByCreatedAt(selectedUser);
        List<OpinionResponseDtoList> opinionResponseDtoList = new ArrayList<>();
        for (Opinion opinion : opinionList) {
            log.info(user.getUsername());
            Integer likeCount = opinionLikeRepository.countAllByOpinion(opinion);
            Boolean likeStat = opinionLikeRepository.findByUserAndOpinion(user, opinion) != null;
            opinionResponseDtoList.add(new OpinionResponseDtoList(opinion, likeStat, likeCount));
        }
        return opinionResponseDtoList;
    }

    public OpinionResponseDtoDetail selectedUserOpinionDetail(Long opinionId, User user) {
        Opinion selectedOpinion = opinionRepository.findById(opinionId).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않습니다.")
        );
        // Opinion Like
        Integer likeCount = opinionLikeRepository.countAllByOpinion(selectedOpinion);
        Boolean likeStat = opinionLikeRepository.findByUserAndOpinion(user, selectedOpinion) != null;
        // Comment Like
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment: selectedOpinion.getCommentList()) {
            comments.add(new CommentResponseDto(
                    comment,
                    commentLikeRepository.findByUserAndComment(user, comment) != null,
                    commentLikeRepository.countAllByComment(comment)
            ));
        }
        return new OpinionResponseDtoDetail(selectedOpinion, likeStat, likeCount, comments);
    }

    @Transactional
    public OpinionResponseDto opinionUpdate(Long id, OpinionRequestDto requestDto, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
        Opinion opinion = findById(id);
        if (opinion.getUsername().equals(user.getUsername())) {
            opinion.update(requestDto);
        } else {
            throw new IllegalArgumentException("수정이 불가능합니다.");
        }
        return new OpinionResponseDto(opinion);
    }

    public StringResponseDto opinionDelete(Long id, User user) {
        if (user.getRole().equals(UserRoleEnum.voterUser)) {
            throw new IllegalArgumentException("사용할 수 없는 기능입니다.");
        }
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
