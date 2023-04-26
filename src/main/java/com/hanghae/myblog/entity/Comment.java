package com.hanghae.myblog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String content;

    @Builder
    public Comment(String content, Article article, User user) {
        this.article = article;
        this.content = content;
        this.user = user;
    }

    public Comment updateComment(String content){
        this.content = content;
        return this;
    }
}
