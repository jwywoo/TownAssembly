package com.example.townassembly.domain.post.opinion.repository;

import com.example.townassembly.domain.post.opinion.dto.OpinionResponseDto;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findAllByUsernameOrderByModifiedAtDesc(String username);

    List<Opinion> findAllByUserOrderByCreatedAt(User selectedUser);

    Opinion findByUserAndId(User user, Long opinionId);
}
