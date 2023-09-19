package com.example.townassembly.domain.post.campaign.entity;

import com.example.townassembly.domain.post.campaign.dto.CampaignRequestDto;
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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name="thumbnail")
    private byte[] imageThumbnail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Campaign(CampaignRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
        this.imageThumbnail = requestDto.getImageThumbnail();
        this.username = user.getUsername();
        this.user = user;
    }

    public void update(CampaignRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
        this.imageThumbnail = requestDto.getImageThumbnail();
    }
}
