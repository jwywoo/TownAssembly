package com.example.townassembly.domain.comment.comment.repository;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUsernameOrderByModifiedAtDesc(String username);
}
