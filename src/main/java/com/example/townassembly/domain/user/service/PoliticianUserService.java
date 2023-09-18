package com.example.townassembly.domain.user.service;

import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.domain.user.entity.PoliticianUser;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.repository.PoliticianUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.townassembly.domain.user.entity.UserRoleEnum.Authority.voterUser;

@Service
@RequiredArgsConstructor
public class PoliticianUserService {

    private final PoliticianUserRepository politicianUserRepository;
    private final PasswordEncoder passwordEncoder;

    private PoliticianUser politicianUser;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<PoliticianUser> checkUsername = politicianUserRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return ResponseEntity.status(400).body("상태코드 : " + HttpStatus.BAD_REQUEST.value() + ", 메세지 : 중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        if(requestDto.isVoterUser()) {
            role = UserRoleEnum.voterUser;
            politicianUser.getOpinionList().clear();
            politicianUser.getCampaignList().clear();
        }

        // 사용자 등록
        PoliticianUser politicianUser = new PoliticianUser(username, password, role);
        politicianUserRepository.save(politicianUser);
        return ResponseEntity.status(200).body("상태코드 : " + HttpStatus.OK.value() + ", 메세지 : 회원가입 성공");
    }


}
