package com.hanghae.myblog.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String content;

    @Builder
    private Comment(User user, Article article, String content) {
        this.user = user;
        this.article = article;
        this.content = content;
        article.getComments().add(this);
    }

    public Comment updateComment(String content){
        this.content=content;
        return this;
    }
}