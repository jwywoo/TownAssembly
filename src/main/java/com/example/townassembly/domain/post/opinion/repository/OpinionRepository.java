package com.example.townassembly.domain.post.opinion.repository;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findAllByUsernameOrderByModifiedAtDesc(String username);
}
