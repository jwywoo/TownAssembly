package com.example.townassembly.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "voterUser")
public class VoterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String voterId;

    @Column(nullable = false)
    private String voterPw;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String preferredParty;

    @Column(nullable = true)
    private String district;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public VoterUser(String voterId, String voterPw, UserRoleEnum role) {
        this.voterId = voterId;
        this.voterPw = voterPw;
        this.role = role;
    }

//    public VoterUser(SignupRequestDto requestDto) {
//        this.voterId = requestDto.getVoterId();
//        this.voterPw = requestDto.getVoterPw();
//        this.role = requestDto.isAdminCheck() ? UserRoleEnum.USER : UserRoleEnum.ADMIN;
//    }


}
