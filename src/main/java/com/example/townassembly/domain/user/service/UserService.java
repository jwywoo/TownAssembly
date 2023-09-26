package com.example.townassembly.domain.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import com.example.townassembly.domain.post.opinion.repository.OpinionRepository;
import com.example.townassembly.domain.user.dto.*;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.follow.entity.Follow;
import com.example.townassembly.domain.user.follow.repository.FollowRepository;
import com.example.townassembly.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OpinionRepository opinionRepository;
    private final FollowRepository followRepository;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public List<SignupResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String party = requestDto.getParty();
        String location = requestDto.getLocation();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 활동명 중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 활동명이 존재합니다.");
        }

        // 이메일 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
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
        if (party.isEmpty() || location.isEmpty()) {
            role = UserRoleEnum.voterUser;
            User user = new User(username, password, role, nickname, email);
            userRepository.save(user);

            // SignupResponseDto 객체 생성
            SignupResponseDto responseDto = new SignupResponseDto(user);
            List<SignupResponseDto> userInfoList = new ArrayList<>();
            userInfoList.add(responseDto);
            return userInfoList;
        }

        // 사용자 등록
        User user = new User(username, password, role, nickname, email, party, location);
        userRepository.save(user);

        // SignupResponseDto 객체 생성
        SignupResponseDto responseDto = new SignupResponseDto(user);
        List<SignupResponseDto> userInfoList = new ArrayList<>();
        userInfoList.add(responseDto);
        return userInfoList;
    }


    @Transactional
    public List<AllUsersResponseDto> AllUsersList(UserRoleEnum userRoleEnum) {
        // "USER" 권한을 가진 모든 사용자를 가져옵니다.
        List<User> users = userRepository.findAllByRole(UserRoleEnum.USER);
        // findBy -> 1 (x)
        // findAllBy -> N

        // users 리스트와 해당 사용자의 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            // 각 사용자의 최신 의견을 가져옵니다.
            //
            List<Opinion> currUserOpinions = opinionRepository.findAllByUserOrderByCreatedAt(user);
            if (currUserOpinions.size() == 0) {
                userResponseDtos.add(new AllUsersResponseDto(user, ""));
            } else {
                userResponseDtos.add(new AllUsersResponseDto(user, currUserOpinions.get(0).getTitle()));
            }
        }
        return userResponseDtos;
    }

    @Transactional
    public List<AllUsersResponseDto> LocationUsersList(String location, User users) {
        // 해당 위치 정보를 가진 모든 사용자를 가져옵니다.
        List<User> usersLocation = userRepository.findAllByLocation(location);

        // 사용자 정보와 해당 사용자의 최신 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> usersLocationDtos = new ArrayList<>();
        for (User user : usersLocation) {
            // 각 사용자의 최신 의견을 가져옵니다.
            List<Opinion> currUserOpinions = opinionRepository.findAllByUserOrderByCreatedAt(user);
            if (currUserOpinions.size() == 0) {
                usersLocationDtos.add(new AllUsersResponseDto(user, ""));
            } else {
                usersLocationDtos.add(new AllUsersResponseDto(user, currUserOpinions.get(0).getTitle()));
            }
        }
        return usersLocationDtos;
    }

    @Transactional
    public List<AllUsersResponseDto> PartyUsersList(String party, User users) {
        // 해당 정당 정보를 가진 모든 사용자를 가져옵니다.
        List<User> usersParty = userRepository.findAllByParty(party);

        // 사용자 정보와 해당 사용자의 최신 의견 정보를 가져옵니다.
        List<AllUsersResponseDto> usersPartyDtos = new ArrayList<>();
        for (User user : usersParty) {
            // 각 사용자의 최신 의견을 가져옵니다.
            List<Opinion> currUserOpinions = opinionRepository.findAllByUserOrderByCreatedAt(user);
            if (currUserOpinions.size() == 0) {
                usersPartyDtos.add(new AllUsersResponseDto(user, ""));
            } else {
                usersPartyDtos.add(new AllUsersResponseDto(user, currUserOpinions.get(0).getTitle()));
            }
        }
        return usersPartyDtos;
    }

    @Transactional
    public List<AllUsersResponseDto> FollowingUsersList(User user) {
        // 로그인한 유저가 팔로우한 사람들의 목록을 가져옵니다.
        List<Follow> followingList = followRepository.findByUser(user);

        // 팔로우한 사용자 목록을 저장할 리스트를 초기화합니다.
        List<AllUsersResponseDto> followingUserDtos = new ArrayList<>();

        for (Follow follow : followingList) {
            User followingUser = follow.getForWhom();

            // 각 팔로우한 사용자의 최신 의견을 가져옵니다.
            Opinion latestOpinion = opinionRepository.findAllByUserOrderByCreatedAt(user).get(0);

            // Opinion 객체가 null이면 기본 값을 사용하거나 빈 객체를 생성합니다.
            if (latestOpinion == null) {
                latestOpinion = new Opinion(); // 빈 객체 생성 또는 기본 값을 사용할 수 있음
            }

            // AllUsersResponseDto를 생성하여 리스트에 추가합니다.
            followingUserDtos.add(new AllUsersResponseDto(followingUser, latestOpinion.getTitle()));
        }
        return followingUserDtos;
    }

    @Transactional
    public List<InfoResponseDto> userInfoList(User user) {
        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findById(user.getId());

        if (!userOptional.isPresent()) {
            // 사용자가 존재하지 않는 경우 예외 처리
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        User existingUser = userOptional.get();

        // UserInfoResponseDto 객체 생성 및 사용자 정보 설정
        InfoResponseDto infoResponseDto = new InfoResponseDto(existingUser.getUserIntro(), existingUser.getNickname(), existingUser.getImageUrl());

        // 결과를 리스트에 담아 반환
        List<InfoResponseDto> userInfoResponseDtos = new ArrayList<>();
        userInfoResponseDtos.add(infoResponseDto);

        return userInfoResponseDtos;
    }

    @Transactional
    public boolean followUser(Long id, User user) {
        User targetUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 계정입니다."));

        // 이미 팔로우 중인지 확인
        Follow existingFollow = followRepository.findByUserAndForWhom(user, targetUser);
        if (existingFollow == null) {
            Follow follow = new Follow();
            follow.setUser(user);
            follow.setForWhom(targetUser);
            follow.setFollowStat(true); // 팔로우 상태를 true로 설정
            followRepository.save(follow);
            return true; // 팔로우 성공을 나타내는 값 리턴
        }
        return false; // 이미 팔로우한 상태이므로 팔로우하지 않음을 나타내는 값 리턴
    }

    @Transactional
    public boolean unfollowUser(Long id, User user) {
        User targetUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 계정입니다."));

        // 이미 팔로우 중인지 확인
        Follow existingFollow = followRepository.findByUserAndForWhom(user, targetUser);
        if (existingFollow != null) {
            followRepository.delete(existingFollow);
            return true; // 언팔로우 성공을 나타내는 값 리턴
        }
        return false; // 이미 언팔로우한 상태이므로 언팔로우하지 않음을 나타내는 값 리턴
    }

    public UserDetailPageDto userStatus(Long id, User user) {
        User forWhomUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회원입니다."));
        Boolean followStat = followRepository.findByUserAndForWhom(user, forWhomUser) != null;
        return new UserDetailPageDto(followStat, forWhomUser);
    }
}
