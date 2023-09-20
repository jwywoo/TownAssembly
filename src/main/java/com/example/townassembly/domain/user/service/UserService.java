package com.example.townassembly.domain.user.service;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.dto.AllUsersResponseDto;
import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OpinionRepository opinionRepository;

    private User user;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String passwordConfirm = requestDto.getPasswordConfirm();
        String party = requestDto.getParty();
        String location = requestDto.getLocation();
        String nickname = requestDto.getNickname();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 사용자가 존재합니다.");
        }

//        Optional<User> checkNickname = userRepository.findByNickName(nickname);
//        if (checkNickname.isPresent()) {
//            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 활동명이 존재합니다.");
//        }

        // 비밀번호 확인
        if(!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자가 유권자인지 확인
        if(requestDto.isVoterUser()) {
            role = UserRoleEnum.voterUser;
        }

        // 정당이나 지역이 입력되어 있지 않으면 아이디와 비밀번호, 역할만 받고 그대로 회원가입
        if(party.isEmpty() || location.isEmpty()) {
            User user = new User(username, password, role);
            userRepository.save(user);
            return ResponseEntity.status(200).body("상태코드 : " + HttpStatus.OK.value() + ", 메세지 : 회원가입 성공");
        }

        // 사용자 등록
        User user = new User(username, password, role, party, location);
        userRepository.save(user);
        return ResponseEntity.status(200).body("상태코드 : " + HttpStatus.OK.value() + ", 메세지 : 회원가입 성공");
    }

    public List<AllUsersResponseDto> AllUsersList(UserRoleEnum userRoleEnum) {
        // "USER" 권한을 가진 모든 사용자를 가져옵니다.
        List<User> users = userRepository.findByRole(UserRoleEnum.USER);

        // users 리스트와 해당 사용자의 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users) {
            // 각 사용자의 최신 의견을 가져옵니다.
            Opinion latestOpinion = opinionRepository.findLatestOpinionByUserId(user.getId());

            // AllUsersResponseDto를 생성하여 리스트에 추가합니다.
            userResponseDtos.add(new AllUsersResponseDto(user, latestOpinion));
        }
        return userResponseDtos;
    }

//    public List<AllUsersResponseDto> LocationUsersList(User user) {
//
//    }
}
