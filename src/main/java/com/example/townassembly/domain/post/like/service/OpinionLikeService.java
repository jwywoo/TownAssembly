package com.example.townassembly.domain.post.like.service;

import com.example.townassembly.domain.post.like.entity.OpinionLike;
import com.example.townassembly.domain.post.like.repository.OpinionLikeRepository;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpinionLikeService {
    private final OpinionLikeRepository opinionLikeRepository;
    private final UserRepository userRepository;
    private final OpinionRepository opinionRepository;
    @Transactional
    public void opinionLike(Long id, User user) {
        Opinion opinion = opinionRepository.findById(id) .orElseThrow(
                () -> new IllegalArgumentException("유요하지 않은 정보입니다.")
        );
        OpinionLike opinionLike = opinionLikeRepository.findByUserAndOpinion(user, opinion);

        if (opinionLike == null) {
            opinionLike = new OpinionLike();
            opinionLike.setOpinion(opinion);
            opinionLike.setUser(user);
            opinionLikeRepository.save(opinionLike);
        } else {
            opinionLikeRepository.delete(opinionLike);
        }
    }
}
