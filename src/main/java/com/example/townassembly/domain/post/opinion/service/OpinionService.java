package com.example.townassembly.domain.post.opinion.service;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDtoDetail;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public OpinionResponseDto opinionCreate(OpinionRequestDto requestDto, User user) {

        Opinion newOpinion = new Opinion(requestDto, user);
        user.opinionAdd(newOpinion);
        return new OpinionResponseDto(opinionRepository.save(newOpinion));
    }

    public List<OpinionResponseDto> opinionList(User user) {
        return opinionRepository
                .findAllByUsernameOrderByModifiedAtDesc(user.getUsername())
                .stream()
                .map(OpinionResponseDto::new)
                .toList();
    }

    public List<OpinionResponseDto> selectedOpinionList(Long id) {
        User selectedUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않는 사용자 입니다.")
        );
        return opinionRepository
                .findAllByUserOrderByCreatedAt(selectedUser)
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

    public OpinionResponseDtoDetail selectedOpinionDetail(Long userid, Long opinionId) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalArgumentException("유요하지 않는 회원입니다.")
        );
        Opinion selectedOpinion = opinionRepository.findByUserAndId(user, opinionId);
        return new OpinionResponseDtoDetail(selectedOpinion);
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
