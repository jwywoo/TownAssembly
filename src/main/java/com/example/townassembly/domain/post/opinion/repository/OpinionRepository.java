package com.example.townassembly.domain.post.opinion.repository;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
}
