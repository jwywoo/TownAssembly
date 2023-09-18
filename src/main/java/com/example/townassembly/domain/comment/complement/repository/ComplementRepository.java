package com.example.townassembly.domain.comment.complement.repository;

import com.example.townassembly.domain.comment.complement.entity.Complement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplementRepository extends JpaRepository<Complement, Long> {
    List<Complement> findAllByUsernameOrderByModifiedAtDesc(String username);
}
