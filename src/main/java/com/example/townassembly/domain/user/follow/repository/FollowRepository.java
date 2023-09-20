package com.example.townassembly.domain.user.follow.repository;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByUserAndForWhom(User user, User forWhom);
    List<Follow> findByUser(User user);

}
