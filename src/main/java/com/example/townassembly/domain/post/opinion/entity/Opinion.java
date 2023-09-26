package com.example.townassembly.domain.post.opinion.entity;

import com.example.townassembly.domain.comment.comment.entity.Comment;
import com.example.townassembly.domain.post.campaign.dto.CampaignRequestModel;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestDto;
import com.example.townassembly.domain.post.opinion.dto.OpinionRequestModel;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "opinion")
@NoArgsConstructor
public class Opinion extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name="nickname", nullable = false)
    private String nickname;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name="role")
    private UserRoleEnum role;
    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "opinion")
    private List<Comment> commentList = new ArrayList<>();

    public Opinion(OpinionRequestModel requestDto, User user, String imageUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.imageUrl = imageUrl;
    }

    public void update(OpinionRequestModel requestDto, String imageUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = imageUrl;
    }

    public void update(OpinionRequestModel requestModel) {
        this.title = requestModel.getTitle();
        this.content = requestModel.getContent();
    }

    public void commentAdd(Comment comment) {
        this.commentList.add(comment);
        comment.setOpinion(this);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
