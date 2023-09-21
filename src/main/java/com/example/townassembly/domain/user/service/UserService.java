package com.example.townassembly.domain.user.service;

import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.dto.AllUsersResponseDto;
import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.dto.UserInfoResponseDto;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.follow.entity.Follow;
import com.example.townassembly.domain.user.follow.repository.FollowRepository;
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
    private final FollowRepository followRepository;

    private User user;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String party = requestDto.getParty();
        String location = requestDto.getLocation();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 사용자가 존재합니다.");
        }

        // 활동명 중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 활동명이 존재합니다.");
        }

        // 이메일 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 이메일이 존재합니다.");
        }

        // 기본으로 정치인 유저로 ROLE 등록
        UserRoleEnum role = UserRoleEnum.USER;

        // 관리자 ROLE 확인
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 정당이나 지역이 입력되어 있지 않으면 아이디와 비밀번호, 역할, 활동명만 입력받고 유권자 유저로 회원가입
        if(party.isEmpty() || location.isEmpty()) {
            role = UserRoleEnum.voterUser;
            User user = new User(username, password, role, nickname, email);
            userRepository.save(user);
            return ResponseEntity.status(200).body("상태코드 : " + HttpStatus.OK.value() + ", 메세지 : 회원가입 성공");
        }

        // 사용자 등록
        User user = new User(username, password, role, nickname, email, party, location);
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

    public List<AllUsersResponseDto> LocationUsersList(String location) {
        // 해당 위치 정보를 가진 모든 사용자를 가져옵니다.
        List<User> usersLocation = userRepository.findByLocation(location);

        // 사용자 정보와 해당 사용자의 최신 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> usersLocationDtos = new ArrayList<>();

        for (User user : usersLocation) {
            // 각 사용자의 최신 의견을 가져옵니다.
            Opinion latestOpinion = opinionRepository.findLatestOpinionByUserId(user.getId());

            // Opinion 객체가 null이면 기본 값을 사용하거나 빈 객체를 생성합니다.
            if (latestOpinion == null) {
                latestOpinion = new Opinion(); // 빈 객체 생성 또는 기본 값을 사용할 수 있음
            }

            // AllUsersResponseDto를 생성하여 리스트에 추가합니다.
            usersLocationDtos.add(new AllUsersResponseDto(user, latestOpinion));
        }
        return usersLocationDtos;
    }

    public List<AllUsersResponseDto> PartyUsersList(String party) {
        // 해당 정당 정보를 가진 모든 사용자를 가져옵니다.
        List<User> usersParty = userRepository.findByParty(party);

        // 사용자 정보와 해당 사용자의 최신 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> usersPartyDtos = new ArrayList<>();

        for (User user : usersParty) {
            // 각 사용자의 최신 의견을 가져옵니다.
            Opinion latestOpinion = opinionRepository.findLatestOpinionByUserId(user.getId());

            // Opinion 객체가 null이면 기본 값을 사용하거나 빈 객체를 생성합니다.
            if (latestOpinion == null) {
                latestOpinion = new Opinion(); // 빈 객체 생성 또는 기본 값을 사용할 수 있음
            }

            // AllUsersResponseDto를 생성하여 리스트에 추가합니다.
            usersPartyDtos.add(new AllUsersResponseDto(user, latestOpinion));
        }
        return usersPartyDtos;
    }

    public List<AllUsersResponseDto> FollowingUsersList(User user) {
        // 로그인한 유저가 팔로우한 사람들의 목록을 가져옵니다.
        List<Follow> followingList = followRepository.findByUser(user);

        // 팔로우한 사용자 목록을 저장할 리스트를 초기화합니다.
        List<AllUsersResponseDto> followingUserDtos = new ArrayList<>();

        for (Follow follow : followingList) {
            User followingUser = follow.getForWhom();

            // 각 팔로우한 사용자의 최신 의견을 가져옵니다.
            Opinion latestOpinion = opinionRepository.findLatestOpinionByUserId(followingUser.getId());

            // Opinion 객체가 null이면 기본 값을 사용하거나 빈 객체를 생성합니다.
            if (latestOpinion == null) {
                latestOpinion = new Opinion(); // 빈 객체 생성 또는 기본 값을 사용할 수 있음
            }

            // AllUsersResponseDto를 생성하여 리스트에 추가합니다.
            followingUserDtos.add(new AllUsersResponseDto(followingUser, latestOpinion));
        }
        return followingUserDtos;
    }

    public List<UserInfoResponseDto> UserInfoList(User user) {
        List<UserInfoResponseDto> userInfoList = new ArrayList<>();

        // 로그인한 유저의 정보를 가져옵니다.
        Optional<User> userOptional = userRepository.findById(user.getId());
        userOptional.ifPresent(loggedInUser -> {
            UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
            userInfoResponseDto.setUserIntro(loggedInUser.getUserIntro());
            userInfoResponseDto.setUserProfilePicture(loggedInUser.getUserProfilePicture());
            userInfoList.add(userInfoResponseDto);
        });

        return userInfoList;
    }
}
