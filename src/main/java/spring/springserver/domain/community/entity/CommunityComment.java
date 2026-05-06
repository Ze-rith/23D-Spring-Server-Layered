package spring.springserver.domain.community.entity;

import jakarta.persistence.*;
import spring.springserver.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommunityPost communityPost;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
