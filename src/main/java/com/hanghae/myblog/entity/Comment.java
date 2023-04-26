package com.hanghae.myblog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


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

    @Column(nullable = false)
    @ColumnDefault("0")
    @Setter
    private int likeCount;

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
