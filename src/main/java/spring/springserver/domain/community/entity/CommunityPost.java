package spring.springserver.domain.community.entity;

import jakarta.persistence.*;
import spring.springserver.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_post")
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    private String content;

    private String imageUrl;

    private int viewCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
