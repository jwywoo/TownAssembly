package com.example.townassembly.domain.user.entity;

import com.example.townassembly.domain.post.campaign.entity.Campaign;
import com.example.townassembly.domain.post.opinion.entity.Opinion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "politicianUser", cascade = CascadeType.REMOVE)
    private List<Opinion> opinionList;

    @OneToMany(mappedBy = "politicianUser", cascade = CascadeType.REMOVE)
    private List<Campaign> campaignList;

    //    @Column(nullable = true)
//    private String email;
//
//    @Column(nullable = true)
//    private String preferredParty;
//
//    @Column(nullable = true)
//    private String district;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.opinionList = new ArrayList<>();
        this.campaignList = new ArrayList<>();

//        if (role == UserRoleEnum.voterUser) {
//            this.opinionList = null;
//            this.campaignList = null;
//        } else {
//            this.opinionList = new ArrayList<>();
//            this.campaignList = new ArrayList<>();
//        }

    }
}
