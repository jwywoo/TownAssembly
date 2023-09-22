package com.example.townassembly.domain.post.campaign.entity;

import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
import com.example.townassembly.domain.post.campaign.dto.CampaignRequestModel;
import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "campaign")
public class Campaign extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name = "username", nullable = false)
    private String username;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Campaign(CampaignRequestModel requestDto, User user, String imageUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
        this.imageUrl = imageUrl;
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }

    public void update(CampaignRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
//        this.imageUrl = imageUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
