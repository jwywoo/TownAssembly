package com.example.townassembly.domain.post.like.repository;

import com.example.townassembly.domain.post.like.entity.OpinionLike;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionLikeRepository extends JpaRepository<OpinionLike, Long> {
    OpinionLike findByUserAndOpinion(User user, Opinion opinion);
    Integer countAllByOpinion(Opinion opinion);
}
