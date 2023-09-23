package com.example.townassembly.domain.user.service;

import com.example.townassembly.domain.user.dto.ModifyPasswordRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoResponseDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.dto.JsonResponseDto;
import com.example.townassembly.global.s3.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;


    public List<UserInfoResponseDto> modifyProfile(User user) {
        List<UserInfoResponseDto> userInfoResponseDtos = new ArrayList<>();

        // 로그인한 유저의 정보를 가져옵니다.
        Optional<User> userOptional = userRepository.findById(user.getId());

        userOptional.ifPresent(loggedInUser -> {
            UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(user);
            userInfoResponseDto.setUsername(loggedInUser.getUsername());
            userInfoResponseDto.setUserIntro(loggedInUser.getUserIntro());
            userInfoResponseDto.setUserNickname(loggedInUser.getNickname());
            userInfoResponseDto.setEmail(loggedInUser.getEmail());
            userInfoResponseDto.setParty(loggedInUser.getParty());
            userInfoResponseDtos.add(userInfoResponseDto);
        });
        return userInfoResponseDtos;
    }


    @Transactional
    public List<UserInfoResponseDto> modifyProfileSave(User user, UserInfoRequestDto userInfoRequestDto) {
        List<UserInfoResponseDto> userInfoResponseDtos = new ArrayList<>();

        // 로그인한 유저의 정보를 가져옵니다.
        Optional<User> userOptional = userRepository.findById(user.getId());

        userOptional.ifPresent(loggedInUser -> {
            // 여기서 UserInfoRequestDto의 필드를 사용하여 유저 정보를 업데이트합니다.
            if (userInfoRequestDto.getUserIntro() != null) {
                loggedInUser.setUserIntro(userInfoRequestDto.getUserIntro());
            }
            if (userInfoRequestDto.getNickname() != null) {
                loggedInUser.setNickname(userInfoRequestDto.getNickname());
            }
            if (userInfoRequestDto.getEmail() != null) {
                loggedInUser.setEmail(userInfoRequestDto.getEmail());
            }
            if (userInfoRequestDto.getParty() != null) {
                loggedInUser.setParty(userInfoRequestDto.getParty());
            }
            if (userInfoRequestDto.getLocation() != null) {
                loggedInUser.setLocation(userInfoRequestDto.getLocation());
            }

            // 업데이트된 유저 정보를 저장합니다.
            userRepository.save(loggedInUser);

            // 업데이트된 정보를 UserInfoResponseDto에 담아서 리스트에 추가합니다.
            UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(loggedInUser);
            userInfoResponseDto.setUserIntro(loggedInUser.getUserIntro());
            userInfoResponseDto.setUserNickname(loggedInUser.getNickname());
            userInfoResponseDto.setEmail(loggedInUser.getEmail());
            userInfoResponseDto.setParty(loggedInUser.getParty());
            userInfoResponseDto.setLocation(loggedInUser.getLocation());
            userInfoResponseDtos.add(userInfoResponseDto);
        });
        return userInfoResponseDtos;
    }


    @Transactional
    public UserInfoResponseDto uploadProfileImage(User user, MultipartFile image)
            throws IOException {
        User user1 = userRepository.findById(user.getId()).orElseThrow(null);

        if (!image.isEmpty()) {
            String fileName = s3Uploader.upload(image, "userProfile");
            user1.update(fileName);
            return new UserInfoResponseDto(user1.getImageUrl());
        } else {
            throw new IllegalArgumentException("사진이 없습니다. 사진을 넣어주세요.");
        }
    }

    @Transactional
    public ResponseEntity<JsonResponseDto> modifyPassword(User user,
            @RequestBody ModifyPasswordRequestDto modifyPasswordRequestDto) {

        // 현재 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(modifyPasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponseDto(HttpStatus.BAD_REQUEST.value(), "현재 비밀번호가 일치하지 않습니다."));
        }

        // 새 비밀번호로 업데이트
        String encodedNewPassword = passwordEncoder.encode(modifyPasswordRequestDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        return ResponseEntity.ok(new JsonResponseDto(HttpStatus.OK.value(), "비밀번호가 성공적으로 변경되었습니다."));
    }
}
