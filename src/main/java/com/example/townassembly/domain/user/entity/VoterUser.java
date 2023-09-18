package com.example.townassembly.domain.user.entity;

import com.example.townassembly.domain.user.dto.SignupRequestDto;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "voterUser")
public class VoterUser extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = true)
//    private String email;
//
//    @Column(nullable = true)
//    private String preferredParty;
//
//    @Column(nullable = true)
//    private String district;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public VoterUser(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
