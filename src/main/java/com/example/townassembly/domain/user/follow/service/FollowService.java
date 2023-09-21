package com.example.townassembly.domain.user.follow.service;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.follow.dto.FollowRequestDto;
import com.example.townassembly.domain.user.follow.entity.Follow;
import com.example.townassembly.domain.user.follow.repository.FollowRepository;
import com.example.townassembly.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void followCreate(FollowRequestDto followRequestDto, User user) {
        User forWhom = userRepository.findById(followRequestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 계정입니다."));

//        // 스스로를 팔로우할 수 없도록 확인합니다.
//        if (user.equals(forWhom)) {
//            throw new IllegalArgumentException("스스로를 팔로우 할 수 없습니다.");
//        }

        Follow follow = followRepository.findByUserAndForWhom(user, forWhom);

        if (follow == null) {
            follow = new Follow();
            follow.setUser(user);
            follow.setForWhom(forWhom);
            followRepository.save(follow);
        } else {
            followRepository.delete(follow);
        }
    }
}
