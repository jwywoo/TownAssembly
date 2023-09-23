package com.example.townassembly.domain.user.service;

import com.example.townassembly.domain.user.dto.UserInfoRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoResponseDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.UserRepository;
import com.example.townassembly.global.s3.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    // 내 정보 수정 페이지에 들어갔을 때 필요한 데이터를 불러온다.
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

    // 내 정보 수정에서 '수정'버튼을 누르면 기존에 저장된 데이터를 업데이트 한다.
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
            return new UserInfoResponseDto(user1.getUserProfilePicture());
        } else {
            throw new IllegalArgumentException("사진이 없습니다. 사진을 넣어주세요.");
        }
    }
}
