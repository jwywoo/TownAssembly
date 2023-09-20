package com.example.townassembly.domain.user.entity;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.comment.complement.entity.Complement;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Opinion> opinionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Campaign> campaignList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Complement> complementList = new ArrayList<>();

//  @Column(nullable = true)
//  private String email;

    @Column(nullable = true)
    private String party;

    @Column(nullable = true)
    private String location;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, UserRoleEnum role, String party, String location) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.party = party;
        this.location = location;
    }

    public void commentAdd(Comment comment) {
        this.commentList.add(comment);
        comment.setUser(this);
    }
    public void complementAdd(Complement complement) {
        this.complementList.add(complement);
        complement.setUser(this);
    }
    public void campaignAdd(Campaign campaign) {
        this.campaignList.add(campaign);
        campaign.setUser(this);
    }
    public void opinionAdd(Opinion opinion) {
        this.opinionList.add(opinion);
        opinion.setUser(this);
    }
}
